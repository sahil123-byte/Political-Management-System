package com.pms.political_management_system.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

// Stand-in for a real email provider (SendGrid / Amazon SES). Replace this
// class's body with an actual API call once credentials are available -
// everything upstream (outbox, Kafka topics, consumer) stays the same.
@Component
public class EmailChannelProvider implements ChannelProvider {

    private static final Logger logger = LoggerFactory.getLogger(EmailChannelProvider.class);

    @Override
    public String channel() {
        return "EMAIL";
    }

    @Override
    public void send(NotificationEvent event) {
        logger.info("[EMAIL] To member {}: \"{}\" - {}",
                event.getMemberId(), event.getTitle(), event.getMessage());
    }
}
