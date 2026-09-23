package com.pms.political_management_system.dto.response;

public class StateResponseDTO {

    private Long id;
    private String stateName;

    public StateResponseDTO() {
    }

    public StateResponseDTO(Long id, String stateName) {
        this.id = id;
        this.stateName = stateName;
    }

    public Long getId() {
        return id;
    }

    public String getStateName() {
        return stateName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}