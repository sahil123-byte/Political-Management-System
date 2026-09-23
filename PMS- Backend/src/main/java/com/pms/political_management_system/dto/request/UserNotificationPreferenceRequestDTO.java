package com.pms.political_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserNotificationPreferenceRequestDTO {

    @NotBlank(message = "Channel Type is required")
    private String channelType;

    @NotNull(message = "isEnabled is required")
    private Boolean isEnabled;

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
}
