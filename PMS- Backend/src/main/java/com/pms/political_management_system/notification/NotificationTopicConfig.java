package com.pms.political_management_system.notification;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

// Declares the topics as beans so Spring Kafka creates them (via
// KafkaAdmin) with sensible partition counts on startup against a real
// broker, rather than relying on topic auto-creation - which many Kafka
// deployments disable.
@Configuration
public class NotificationTopicConfig {

    @Bean
    public NewTopic emailTopic() {
        return TopicBuilder.name(NotificationTopics.EMAIL).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic smsTopic() {
        return TopicBuilder.name(NotificationTopics.SMS).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic inAppTopic() {
        return TopicBuilder.name(NotificationTopics.INAPP).partitions(3).replicas(1).build();
    }

    @Bean
    public NewTopic dlqTopic() {
        return TopicBuilder.name(NotificationTopics.DLQ).partitions(1).replicas(1).build();
    }
}
