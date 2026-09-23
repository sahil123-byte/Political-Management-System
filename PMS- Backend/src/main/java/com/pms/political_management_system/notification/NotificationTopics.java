package com.pms.political_management_system.notification;

// Topic names the outbox publisher writes to and the consumers listen on -
// one per channel, plus a dead-letter topic for events that fail to process
// (mirrors the notifications.<priority>.<channel> / notifications.dlq
// naming in the architecture diagram, simplified to one topic per channel
// since this app doesn't yet distinguish notification priority).
public final class NotificationTopics {

    public static final String EMAIL = "notifications.email";
    public static final String SMS = "notifications.sms";
    public static final String INAPP = "notifications.inapp";
    public static final String DLQ = "notifications.dlq";

    private NotificationTopics() {
    }

    public static String topicFor(String channel) {
        return switch (channel) {
            case "EMAIL" -> EMAIL;
            case "SMS" -> SMS;
            case "INAPP" -> INAPP;
            default -> DLQ;
        };
    }
}
