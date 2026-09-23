package com.pms.political_management_system.dto.response;

import java.time.LocalDate;

public class MemberResponseDTO {

    private Long id;
    private String name;
    private String mobile;
    private String email;
    private String address;
    private String designation;
    private LocalDate joiningDate;

    private Long boothId;
    private String boothName;

    private String imageUrl;

    public MemberResponseDTO() {
    }

    public MemberResponseDTO(Long id,
                             String name,
                             String mobile,
                             String email,
                             String address,
                             String designation,
                             LocalDate joiningDate,
                             Long boothId,
                             String boothName,
                             String imageUrl) {

        this.id = id;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.boothId = boothId;
        this.boothName = boothName;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Long getBoothId() {
        return boothId;
    }

    public void setBoothId(Long boothId) {
        this.boothId = boothId;
    }

    public String getBoothName() {
        return boothName;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
