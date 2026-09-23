package com.pms.political_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DistrictRequestDTO {

    @NotBlank(message = "District name is required")
    private String districtName;

    @NotNull(message = "State Id is required")
    private Long stateId;

    public DistrictRequestDTO() {
    }

    public DistrictRequestDTO(String districtName, Long stateId) {
        this.districtName = districtName;
        this.stateId = stateId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
}