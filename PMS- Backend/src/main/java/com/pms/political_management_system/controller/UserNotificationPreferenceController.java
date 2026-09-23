package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.UserNotificationPreferenceRequestDTO;
import com.pms.political_management_system.dto.response.UserNotificationPreferenceResponseDTO;
import com.pms.political_management_system.service.UserNotificationPreferenceService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Self-service only - every logged-in user manages their own channel
// preferences. There's no admin endpoint to change someone else's, by
// design (matches the "Opt-in/Opt-out" box on the User Preference
// Service in the architecture diagram).
@RestController
@RequestMapping("/api/notification-preferences")
public class UserNotificationPreferenceController {

    private final UserNotificationPreferenceService preferenceService;

    public UserNotificationPreferenceController(UserNotificationPreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public List<UserNotificationPreferenceResponseDTO> getMyPreferences() {
        return preferenceService.getMyPreferences();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/me")
    public UserNotificationPreferenceResponseDTO saveMyPreference(
            @Valid @RequestBody UserNotificationPreferenceRequestDTO requestDTO) {
        return preferenceService.saveMyPreference(requestDTO);
    }
}
