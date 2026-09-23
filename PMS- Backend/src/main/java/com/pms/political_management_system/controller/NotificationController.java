package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.NotificationRequestDTO;
import com.pms.political_management_system.dto.response.NotificationResponseDTO;
import com.pms.political_management_system.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Get All Notifications
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<NotificationResponseDTO> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    // Get Notification By Id
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public NotificationResponseDTO getNotificationById(@PathVariable Long id) {
        return notificationService.getNotificationById(id);
    }

    // Create Notification
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public NotificationResponseDTO saveNotification(
            @Valid @RequestBody NotificationRequestDTO requestDTO) {

        return notificationService.saveNotification(requestDTO);
    }

    // Update Notification
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public NotificationResponseDTO updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody NotificationRequestDTO requestDTO) {

        return notificationService.updateNotification(id, requestDTO);
    }

    // Delete Notification
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public String deleteNotification(@PathVariable Long id) {
        return notificationService.deleteNotification(id);
    }
}