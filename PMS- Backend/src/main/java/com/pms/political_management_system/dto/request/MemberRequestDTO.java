package com.pms.political_management_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MemberRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String mobile;

    @Email
    private String email;

    private String address;

    private String designation;

    private LocalDate joiningDate;

    @NotNull
    private Long boothId;

    private String imageUrl;

    public MemberRequestDTO() {
    }

    public MemberRequestDTO(String name, String mobile, String email,
                            String address, String designation,
                            LocalDate joiningDate, Long boothId, String imageUrl) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.designation = designation;
        this.joiningDate = joiningDate;
        this.boothId = boothId;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}