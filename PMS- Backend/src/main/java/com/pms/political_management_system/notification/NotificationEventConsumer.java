package com.pms.political_management_system.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.political_management_system.entity.User;
import com.pms.political_management_system.repository.UserRepository;
import com.pms.political_management_system.service.UserNotificationPreferenceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// One listener per channel topic. Each checks the recipient's channel
// preference (User Preference Cache -> Channel Providers in the
// architecture diagram - simplified here to a direct DB read instead of a
// Redis-backed cache) before handing off to that channel's provider.
@Component
public class NotificationEventConsumer {

    private static final Logger logger = LoggerFactory.getLogger(NotificationEventConsumer.class);

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final UserNotificationPreferenceService preferenceService;
    private final Map<String, ChannelProvider> providersByChannel;

    public NotificationEventConsumer(ObjectMapper objectMapper,
                                     UserRepository userRepository,
                                     UserNotificationPreferenceService preferenceService,
                                     List<ChannelProvider> providers) {
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
        this.preferenceService = preferenceService;
        this.providersByChannel = providers.stream()
                .collect(Collectors.toMap(ChannelProvider::channel, p -> p));
    }

    @KafkaListener(topics = NotificationTopics.EMAIL, groupId = "pms-notifications")
    public void onEmail(String message) {
        dispatch(message);
    }

    @KafkaListener(topics = NotificationTopics.SMS, groupId = "pms-notifications")
    public void onSms(String message) {
        dispatch(message);
    }

    @KafkaListener(topics = NotificationTopics.INAPP, groupId = "pms-notifications")
    public void onInApp(String message) {
        dispatch(message);
    }

    private void dispatch(String message) {

        try {

            NotificationEvent event = objectMapper.readValue(message, NotificationEvent.class);

            Long userId = userRepository.findByMemberId(event.getMemberId())
                    .map(User::getId)
                    .orElse(null);

            // No linked login for this member (or preference explicitly
            // turned off) - default is opted-in, so only a linked account
            // that has explicitly disabled this channel gets skipped here.
            if (userId != null && !preferenceService.isChannelEnabled(userId, event.getChannel())) {

                logger.info("Skipping {} for member {} - channel disabled by user preference.",
                        event.getChannel(), event.getMemberId());

                return;
            }

            ChannelProvider provider = providersByChannel.get(event.getChannel());

            if (provider != null) {
                provider.send(event);
            } else {
                logger.warn("No ChannelProvider registered for channel {}", event.getChannel());
            }

        } catch (Exception e) {
            // A real deployment would route this to notifications.dlq for
            // inspection instead of just logging - kept simple here.
            logger.error("Failed to process notification event: {}", e.getMessage(), e);
        }
    }
}
