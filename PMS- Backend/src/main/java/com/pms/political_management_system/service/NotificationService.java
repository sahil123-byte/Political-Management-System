package com.pms.political_management_system.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pms.political_management_system.dto.request.NotificationRequestDTO;
import com.pms.political_management_system.dto.response.NotificationResponseDTO;
import com.pms.political_management_system.entity.Member;
import com.pms.political_management_system.entity.Notification;
import com.pms.political_management_system.entity.NotificationOutbox;
import com.pms.political_management_system.notification.NotificationEvent;
import com.pms.political_management_system.repository.MemberRepository;
import com.pms.political_management_system.repository.NotificationOutboxRepository;
import com.pms.political_management_system.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CurrentUserService currentUserService;

    @Autowired
    private NotificationOutboxRepository notificationOutboxRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Get All Notifications
    public List<NotificationResponseDTO> getAllNotifications() {

        // MEMBER role: "Own" access - only see notifications addressed to them.
        if (currentUserService.isMember()) {

            Long memberId = currentUserService.getCurrentMemberId();

            if (memberId == null) {
                return List.of();
            }

            return notificationRepository.findAll()
                    .stream()
                    .filter(n -> n.getMember() != null && memberId.equals(n.getMember().getId()))
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        }

        return notificationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save Notification - also writes a transactional outbox row so the
    // event pipeline (NotificationOutboxPublisher -> Kafka -> channel
    // consumers) picks it up and fans it out to email/sms/in-app, without
    // ever losing the event even if Kafka happens to be down right now.
    @Transactional
    public NotificationResponseDTO saveNotification(NotificationRequestDTO requestDTO) {

        if (requestDTO.getMemberId() == null) {
            throw new RuntimeException("Member is required");
        }

        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

        Notification notification = new Notification();

        notification.setTitle(requestDTO.getTitle());
        notification.setMessage(requestDTO.getMessage());
        notification.setNotificationType(requestDTO.getNotificationType());
        notification.setCreatedAt(requestDTO.getCreatedAt());
        notification.setIsRead(false);
        notification.setMember(member);

        Notification saved = notificationRepository.save(notification);

        writeOutboxEvent(saved);

        return mapToResponse(saved);
    }

    // Get Notification By Id
    public NotificationResponseDTO getNotificationById(Long id) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification Not Found"));

        return mapToResponse(notification);
    }

    // Update Notification
    public NotificationResponseDTO updateNotification(Long id,
                                                      NotificationRequestDTO requestDTO) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification Not Found"));

        if (requestDTO.getMemberId() == null) {
            throw new RuntimeException("Member is required");
        }

        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

        notification.setTitle(requestDTO.getTitle());
        notification.setMessage(requestDTO.getMessage());
        notification.setNotificationType(requestDTO.getNotificationType());
        notification.setCreatedAt(requestDTO.getCreatedAt());
        notification.setMember(member);

        Notification updated = notificationRepository.save(notification);

        return mapToResponse(updated);
    }

    // Delete Notification
    public String deleteNotification(Long id) {

        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification Not Found"));

        notificationRepository.delete(notification);

        return "Notification Deleted Successfully";
    }

    // Writes the outbox row in the caller's transaction (saveNotification
    // is @Transactional) - if this fails, the whole save rolls back rather
    // than leaving a Notification with no corresponding event.
    private void writeOutboxEvent(Notification notification) {

        try {

            NotificationEvent event = new NotificationEvent(
                    notification.getId(),
                    notification.getMember() != null ? notification.getMember().getId() : null,
                    notification.getTitle(),
                    notification.getMessage(),
                    notification.getNotificationType(),
                    null // channel is decided per-topic by the outbox publisher
            );

            String payload = objectMapper.writeValueAsString(event);

            NotificationOutbox outbox = new NotificationOutbox(
                    notification.getId(),
                    "notification.created",
                    payload
            );

            notificationOutboxRepository.save(outbox);

        } catch (Exception e) {
            logger.error("Failed to serialize outbox event for notification {}: {}",
                    notification.getId(), e.getMessage());
            throw new RuntimeException("Failed to queue notification event", e);
        }
    }

    // Mapping Method (null-safe against a missing/deleted member link)
    private NotificationResponseDTO mapToResponse(Notification n) {

        return new NotificationResponseDTO(
                n.getId(),
                n.getTitle(),
                n.getMessage(),
                n.getNotificationType(),
                n.getIsRead(),
                n.getCreatedAt(),
                n.getMember() != null ? n.getMember().getId() : null,
                n.getMember() != null ? n.getMember().getName() : null
        );
    }
}
