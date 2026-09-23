package com.pms.political_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VoterRequestDTO {

    @NotBlank(message = "Voter ID is required")
    private String voterId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Father Name is required")
    private String fatherName;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotNull(message = "Age is required")
    private Integer age;

    @NotBlank(message = "Mobile is required")
    private String mobile;

    @NotBlank(message = "Address is required")
    private String address;

    @NotNull(message = "Booth Id is required")
    private Long boothId;

    private String imageUrl;

    public VoterRequestDTO() {
    }

    public VoterRequestDTO(String voterId,
                           String name,
                           String fatherName,
                           String gender,
                           Integer age,
                           String mobile,
                           String address,
                           Long boothId,
                           String imageUrl) {
        this.voterId = voterId;
        this.name = name;
        this.fatherName = fatherName;
        this.gender = gender;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
        this.boothId = boothId;
        this.imageUrl = imageUrl;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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