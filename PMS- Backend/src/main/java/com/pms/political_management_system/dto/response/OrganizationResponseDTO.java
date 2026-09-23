package com.pms.political_management_system.dto.response;

public class OrganizationResponseDTO {

    private Long id;
    private String organizationName;
    private String organizationType;
    private String headName;
    private String contactNumber;
    private String email;
    private String address;

    private Long partyId;
    private String partyName;

    public OrganizationResponseDTO() {
    }

    public OrganizationResponseDTO(Long id,
                                   String organizationName,
                                   String organizationType,
                                   String headName,
                                   String contactNumber,
                                   String email,
                                   String address,
                                   Long partyId,
                                   String partyName) {
        this.id = id;
        this.organizationName = organizationName;
        this.organizationType = organizationType;
        this.headName = headName;
        this.contactNumber = contactNumber;
        this.email = email;
        this.address = address;
        this.partyId = partyId;
        this.partyName = partyName;
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

    public Long getPartyId() {
        return partyId;
    }

    public void setPartyId(Long partyId) {
        this.partyId = partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
}