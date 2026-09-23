package com.pms.political_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String organizationName;

    private String organizationType;

    private String headName;

    private String contactNumber;

    private String email;

    private String address;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party party;

    public Organization() {
    }

    public Organization(Long id, String organizationName,
                        String organizationType,
                        String headName,
                        String contactNumber,
                        String email,
                        String address,
                        Party party) {
        this.id = id;
        this.organizationName = organizationName;
        this.organizationType = organizationType;
        this.headName = headName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.party = party;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(String organizationType) {
        this.organizationType = organizationType;
    }

    public String getHeadName() {
        return headName;
    }

    public void setHeadName(String headName) {
        this.headName = headName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}