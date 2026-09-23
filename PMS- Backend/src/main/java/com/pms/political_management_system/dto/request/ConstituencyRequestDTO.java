package com.pms.political_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ConstituencyRequestDTO {

    @NotBlank(message = "Constituency name is required")
    private String constituencyName;

    @NotNull(message = "District Id is required")
    private Long districtId;

    @NotNull(message = "Party Id is required")
    private Long partyId;

    public ConstituencyRequestDTO() {
    }

    public ConstituencyRequestDTO(String constituencyName,
                                  Long districtId,
                                  Long partyId) {
        this.constituencyName = constituencyName;
        this.districtId = districtId;
        this.partyId = partyId;
    }

    public String getConstituencyName() {
        return constituencyName;
    }

    public void setConstituencyName(String constituencyName) {
        this.constituencyName = constituencyName;
    }

    public Long getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Long districtId) {
        this.districtId = districtId;
    }

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }
}