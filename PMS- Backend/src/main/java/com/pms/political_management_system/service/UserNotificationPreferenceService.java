package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.UserNotificationPreferenceRequestDTO;
import com.pms.political_management_system.dto.response.UserNotificationPreferenceResponseDTO;
import com.pms.political_management_system.entity.User;
import com.pms.political_management_system.entity.UserNotificationPreference;
import com.pms.political_management_system.repository.UserNotificationPreferenceRepository;
import com.pms.political_management_system.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserNotificationPreferenceService {

    private final UserNotificationPreferenceRepository preferenceRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    public UserNotificationPreferenceService(UserNotificationPreferenceRepository preferenceRepository,
                                              UserRepository userRepository,
                                              CurrentUserService currentUserService) {
        this.preferenceRepository = preferenceRepository;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
    }

    // Read by the notification consumers before dispatching on a channel.
    // Opt-out model: no saved row means the channel is enabled by default.
    public boolean isChannelEnabled(Long userId, String channelType) {

        return preferenceRepository.findByUserIdAndChannelType(userId, channelType)
                .map(UserNotificationPreference::getIsEnabled)
                .orElse(true);
    }

    // The logged-in user's own preferences, for the Settings screen.
    public List<UserNotificationPreferenceResponseDTO> getMyPreferences() {

        User user = currentUserService.getCurrentUser();

        if (user == null) {
            return List.of();
        }

        return preferenceRepository.findByUserId(user.getId())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Upsert - create the row if this is the first time the user has
    // touched this channel, otherwise update the existing one.
    public UserNotificationPreferenceResponseDTO saveMyPreference(UserNotificationPreferenceRequestDTO requestDTO) {

        User user = currentUserService.getCurrentUser();

        if (user == null) {
            throw new RuntimeException("Not authenticated");
        }

        UserNotificationPreference preference = preferenceRepository
                .findByUserIdAndChannelType(user.getId(), requestDTO.getChannelType())
                .orElseGet(() -> {
                    UserNotificationPreference p = new UserNotificationPreference();
                    p.setUser(user);
                    p.setChannelType(requestDTO.getChannelType());
                    return p;
                });

        preference.setIsEnabled(requestDTO.getIsEnabled());
        preference.setUpdatedAt(LocalDateTime.now());

        UserNotificationPreference saved = preferenceRepository.save(preference);

        return mapToResponse(saved);
    }

    private UserNotificationPreferenceResponseDTO mapToResponse(UserNotificationPreference p) {

        return new UserNotificationPreferenceResponseDTO(
                p.getId(),
                p.getUser() != null ? p.getUser().getId() : null,
                p.getChannelType(),
                p.getIsEnabled(),
                p.getUpdatedAt()
        );
    }
}
