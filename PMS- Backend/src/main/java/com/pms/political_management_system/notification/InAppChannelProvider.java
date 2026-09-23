package com.pms.political_management_system.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// In-app delivery already has a real destination - the existing
// `notifications` table the frontend polls via GET /api/notifications.
// The row was already written before this event was raised, so this
// provider's job is just to confirm/log the fan-out; a push provider
// (FCM/APNs) could be added here later for mobile push on top of it.
@Component
public class InAppChannelProvider implements ChannelProvider {

    private static final Logger logger = LoggerFactory.getLogger(InAppChannelProvider.class);

    @Override
    public String channel() {
        return "INAPP";
    }

    @Override
    public void send(NotificationEvent event) {
        logger.info("[INAPP] Notification {} ready for member {} in the notifications table.",
                event.getNotificationId(), event.getMemberId());
    }
}
