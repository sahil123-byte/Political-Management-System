package com.pms.political_management_system.dto.response;

public class VoterResponseDTO {

    private Long id;
    private String voterId;
    private String name;
    private String fatherName;
    private String gender;
    private Integer age;
    private String mobile;
    private String address;

    private Long boothId;
    private String boothName;

    private String imageUrl;

    public VoterResponseDTO() {
    }

    public VoterResponseDTO(Long id,
                            String voterId,
                            String name,
                            String fatherName,
                            String gender,
                            Integer age,
                            String mobile,
                            String address,
                            Long boothId,
                            String boothName,
                            String imageUrl) {
        this.id = id;
        this.voterId = voterId;
        this.name = name;
        this.fatherName = fatherName;
        this.gender = gender;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
        this.boothId = boothId;
        this.boothName = boothName;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getVoterId() {
        return voterId;
    }

    public String getName() {
        return name;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getMobile() {
        return mobile;
    }

    public String getAddress() {
        return address;
    }

    public Long getBoothId() {
        return boothId;
    }

    public String getBoothName() {
        return boothName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBoothId(Long boothId) {
        this.boothId = boothId;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
