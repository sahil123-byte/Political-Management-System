package com.pms.political_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;

public class PartyRequestDTO {

    @NotBlank(message = "Party name is required")
    private String partyName;

    private String partySymbol;

    private String partyPresident;

    private String description;

    private String logoUrl;

    public PartyRequestDTO() {
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartySymbol() {
        return partySymbol;
    }

    public void setPartySymbol(String partySymbol) {
        this.partySymbol = partySymbol;
    }

    public String getPartyPresident() {
        return partyPresident;
    }

    public void setPartyPresident(String partyPresident) {
        this.partyPresident = partyPresident;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}