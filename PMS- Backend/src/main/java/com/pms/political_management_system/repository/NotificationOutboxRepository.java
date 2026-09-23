package com.pms.political_management_system.repository;

import com.pms.political_management_system.entity.NotificationOutbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationOutboxRepository extends JpaRepository<NotificationOutbox, Long> {

    // Picked up by NotificationOutboxPublisher on each poll.
    List<NotificationOutbox> findByPublishedFalseOrderByCreatedAtAsc();
}
