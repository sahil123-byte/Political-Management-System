package com.pms.political_management_system.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// Stand-in for a real SMS provider (Twilio / MSG91). Replace this class's
// body with an actual API call once credentials are available.
@Component
public class SmsChannelProvider implements ChannelProvider {

    private static final Logger logger = LoggerFactory.getLogger(SmsChannelProvider.class);

    @Override
    public String channel() {
        return "SMS";
    }

    @Override
    public void send(NotificationEvent event) {
        logger.info("[SMS] To member {}: {}", event.getMemberId(), event.getMessage());
    }
}
