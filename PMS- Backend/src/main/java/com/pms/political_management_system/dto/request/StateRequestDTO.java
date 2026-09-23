package com.pms.political_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;

public class StateRequestDTO {

    @NotBlank(message = "State name is required")
    private String stateName;

    public StateRequestDTO() {
    }

    public StateRequestDTO(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}