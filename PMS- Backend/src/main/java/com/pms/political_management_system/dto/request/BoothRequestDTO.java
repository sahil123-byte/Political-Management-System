package com.pms.political_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BoothRequestDTO {

    @NotBlank
    private String boothNumber;

    @NotBlank
    private String boothName;

    @NotBlank
    private String location;

    @NotNull
    private Long constituencyId;

    public BoothRequestDTO() {
    }

    public BoothRequestDTO(String boothNumber, String boothName, String location, Long constituencyId) {
        this.boothNumber = boothNumber;
        this.boothName = boothName;
        this.location = location;
        this.constituencyId = constituencyId;
    }

    public String getBoothNumber() {
        return boothNumber;
    }

    public void setBoothNumber(String boothNumber) {
        this.boothNumber = boothNumber;
    }

    public String getBoothName() {
        return boothName;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getConstituencyId() {
        return constituencyId;
    }

    public void setConstituencyId(Long constituencyId) {
        this.constituencyId = constituencyId;
    }
}