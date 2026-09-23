package com.pms.political_management_system.dto.response;

public class PartyResponseDTO {

    private Long id;
    private String partyName;
    private String partySymbol;
    private String partyPresident;
    private String description;
    private String logoUrl;

    public PartyResponseDTO() {
    }

    public PartyResponseDTO(Long id, String partyName,
                            String partySymbol,
                            String partyPresident,
                            String description,
                            String logoUrl) {

        this.id = id;
        this.partyName = partyName;
        this.partySymbol = partySymbol;
        this.partyPresident = partyPresident;
        this.description = description;
        this.logoUrl = logoUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
