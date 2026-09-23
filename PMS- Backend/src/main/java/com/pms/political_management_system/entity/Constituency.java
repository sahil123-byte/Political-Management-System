package com.pms.political_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "constituencies")
public class Constituency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String constituencyName;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;

    public Constituency() {
    }

    public Constituency(Long id,
                        String constituencyName,
                        District district,
                        Party party) {
        this.id = id;
        this.constituencyName = constituencyName;
        this.district = district;
        this.party = party;
    }

    public Long getId() {
        return id;
    }

    public String getConstituencyName() {
        return constituencyName;
    }

    public District getDistrict() {
        return district;
    }

    public Party getParty() {
        return party;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setConstituencyName(String constituencyName) {
        this.constituencyName = constituencyName;
    }

    public void setDistrict(District district) {
        this.district = district;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}