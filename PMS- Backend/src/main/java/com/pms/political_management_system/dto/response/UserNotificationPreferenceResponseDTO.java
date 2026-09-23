package com.pms.political_management_system.dto.response;

import java.time.LocalDateTime;

public class UserNotificationPreferenceResponseDTO {

    private Long id;
    private Long userId;
    private String channelType;
    private Boolean isEnabled;
    private LocalDateTime updatedAt;

    public UserNotificationPreferenceResponseDTO() {
    }

    public UserNotificationPreferenceResponseDTO(Long id, Long userId, String channelType,
                                                  Boolean isEnabled, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.channelType = channelType;
        this.isEnabled = isEnabled;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
