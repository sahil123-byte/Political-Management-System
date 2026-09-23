package com.pms.political_management_system.dto.response;

public class DistrictResponseDTO {

    private Long id;
    private String districtName;

    private Long stateId;
    private String stateName;

    public DistrictResponseDTO() {
    }

    public DistrictResponseDTO(Long id,
                               String districtName,
                               Long stateId,
                               String stateName) {
        this.id = id;
        this.districtName = districtName;
        this.stateId = stateId;
        this.stateName = stateName;
    }

    public Long getId() {
        return id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public Long getStateId() {
        return stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}