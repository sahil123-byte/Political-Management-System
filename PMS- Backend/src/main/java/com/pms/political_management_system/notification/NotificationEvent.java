package com.pms.political_management_system.notification;

import java.io.Serializable;

// The event shape carried through the outbox table and Kafka topics.
// Deliberately flat and provider-agnostic so it can move through JSON
// (outbox payload, Kafka message value) without any coupling to JPA
// entities.
public class NotificationEvent implements Serializable {

    private Long notificationId;
    private Long memberId;
    private String title;
    private String message;
    private String notificationType;
    private String channel; // EMAIL, SMS, INAPP

    public NotificationEvent() {
    }

    public NotificationEvent(Long notificationId, Long memberId, String title,
                             String message, String notificationType, String channel) {
        this.notificationId = notificationId;
        this.memberId = memberId;
        this.title = title;
        this.message = message;
        this.notificationType = notificationType;
        this.channel = channel;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    // A copy of this event addressed to a different channel - the outbox
    // publisher fans a single Notification out to one NotificationEvent
    // per enabled channel.
    public NotificationEvent withChannel(String newChannel) {
        return new NotificationEvent(notificationId, memberId, title, message, notificationType, newChannel);
    }
}
