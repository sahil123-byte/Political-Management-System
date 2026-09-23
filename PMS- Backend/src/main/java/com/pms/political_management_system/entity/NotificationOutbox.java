package com.pms.political_management_system.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Transactional Outbox pattern: when a Notification is created, a row is
// written here in the SAME database transaction. A separate poller
// (NotificationOutboxPublisher) reads unpublished rows and publishes them
// to Kafka - this guarantees the event is never lost even if Kafka is
// briefly unreachable, without needing a real CDC/Debezium deployment.
@Entity
@Table(name = "notifications_outbox")
public class NotificationOutbox {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long notificationId;

    // Logical event name, e.g. "notification.created" - lets consumers or
    // future event types share the same outbox table.
    @Column(nullable = false)
    private String eventType;

    // JSON payload - kept as a plain string so this table has no
    // dependency on the shape of any particular event.
    @Column(nullable = false, length = 4000)
    private String payload;

    @Column(nullable = false)
    private Boolean published = false;

    @Column
    private LocalDateTime publishedAt;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public NotificationOutbox() {
    }

    public NotificationOutbox(Long notificationId, String eventType, String payload) {
        this.notificationId = notificationId;
        this.eventType = eventType;
        this.payload = payload;
        this.published = false;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
