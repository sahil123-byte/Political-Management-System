package com.pms.political_management_system.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.political_management_system.entity.NotificationOutbox;
import com.pms.political_management_system.repository.NotificationOutboxRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

// The poller half of the Transactional Outbox pattern - the practical
// stand-in for Debezium CDC in a single-app setup (no separate Kafka
// Connect deployment to run). Every few seconds it picks up outbox rows
// NotificationService wrote in the same DB transaction as the Notification
// itself, and fans each one out to all three channel topics. If Kafka is
// unreachable, rows are simply left unpublished and retried on the next
// poll - nothing is lost.
@Component
public class NotificationOutboxPublisher {

    private static final Logger logger = LoggerFactory.getLogger(NotificationOutboxPublisher.class);

    private static final List<String> CHANNELS = List.of("EMAIL", "SMS", "INAPP");

    private final NotificationOutboxRepository outboxRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public NotificationOutboxPublisher(NotificationOutboxRepository outboxRepository,
                                       KafkaTemplate<String, String> kafkaTemplate,
                                       ObjectMapper objectMapper) {
        this.outboxRepository = outboxRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishPending() {

        List<NotificationOutbox> pending = outboxRepository.findByPublishedFalseOrderByCreatedAtAsc();

        for (NotificationOutbox row : pending) {
            try {

                NotificationEvent baseEvent = objectMapper.readValue(row.getPayload(), NotificationEvent.class);

                for (String channel : CHANNELS) {

                    NotificationEvent channelEvent = baseEvent.withChannel(channel);
                    String json = objectMapper.writeValueAsString(channelEvent);

                    // Block briefly on each send so a broker that's actually
                    // unreachable throws here and the row is left unpublished
                    // for the next poll, instead of being marked published
                    // on a fire-and-forget call whose failure we'd never see.
                    kafkaTemplate.send(
                            NotificationTopics.topicFor(channel),
                            String.valueOf(channelEvent.getMemberId()),
                            json
                    ).get(5, TimeUnit.SECONDS);
                }

                row.setPublished(true);
                row.setPublishedAt(LocalDateTime.now());
                outboxRepository.save(row);

            } catch (Exception e) {
                // Left unpublished on purpose - will be retried on the next
                // poll. A stuck row here (Kafka down, broker unreachable)
                // is exactly what Metrics & Alerting in the architecture
                // diagram would page someone about in a real deployment.
                logger.error("Failed to publish outbox row {}: {}", row.getId(), e.getMessage());
            }
        }
    }
}
