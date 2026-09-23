package com.pms.political_management_system.notification;

// One implementation per delivery channel (email/sms/in-app). Swap in a
// real provider (Twilio, SendGrid/Amazon SES, FCM/APNs) later by adding a
// new @Component that implements this for the same channel() and marking
// it @Primary - nothing else in the pipeline needs to change.
public interface ChannelProvider {

    // EMAIL, SMS, or INAPP - must match the channel value used in Kafka
    // topic names and in UserNotificationPreference rows.
    String channel();

    // Actually deliver the message. Implementations here log instead of
    // calling a real vendor API, since no provider credentials are
    // configured - see each implementation's class comment for how to
    // wire in a real one.
    void send(NotificationEvent event);
}
