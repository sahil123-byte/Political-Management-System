package com.pms.political_management_system.dto.response;

public class ConstituencyResponseDTO {

    private Long id;
    private String constituencyName;

    private Long districtId;
    private String districtName;

    private Long stateId;
    private String stateName;

    private Long partyId;
    private String partyName;

    public ConstituencyResponseDTO() {
    }

    public ConstituencyResponseDTO(Long id,
                                   String constituencyName,
                                   Long districtId,
                                   String districtName,
                                   Long stateId,
                                   String stateName,
                                   Long partyId,
                                   String partyName) {
        this.id = id;
        this.constituencyName = constituencyName;
        this.districtId = districtId;
        this.districtName = districtName;
        this.stateId = stateId;
        this.stateName = stateName;
        this.partyId = partyId;
        this.partyName = partyName;
    }

    public Long getId() {
        return id;
    }

    public String getConstituencyName() {
        return constituencyName;
    }

    public Long getDistrictId() {
        return districtId;
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

    public Long getPartyId() {
        return partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConstituencyName(String constituencyName) {
        this.constituencyName = constituencyName;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
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

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}