package com.pms.political_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "parties")
public class Party {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "party_name", nullable = false)
    private String partyName;

    @Column(name = "party_symbol")
    private String partySymbol;

    @Column(name = "party_president")
    private String partyPresident;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String logoUrl;

    public Party() {
    }

    public Party(Long id, String partyName, String partySymbol, String partyPresident, String description) {
        this.id = id;
        this.partyName = partyName;
        this.partySymbol = partySymbol;
        this.partyPresident = partyPresident;
        this.description = description;
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