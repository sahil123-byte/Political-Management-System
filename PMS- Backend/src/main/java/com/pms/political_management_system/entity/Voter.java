package com.pms.political_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "voters")
public class Voter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voter_id", nullable = false, unique = true)
    private String voterId;

    private String name;

    @Column(name = "father_name")
    private String fatherName;

    private String gender;

    private Integer age;

    private String mobile;

    private String address;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "booth_id")
    private Booth booth;

    public Voter() {
    }

    public Voter(Long id, String voterId, String name, String fatherName,
                 String gender, Integer age, String mobile,
                 String address, Booth booth) {
        this.id = id;
        this.voterId = voterId;
        this.name = name;
        this.fatherName = fatherName;
        this.gender = gender;
        this.age = age;
        this.mobile = mobile;
        this.address = address;
        this.booth = booth;
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

    public Booth getBooth() {
        return booth;
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

    public void setBooth(Booth booth) {
        this.booth = booth;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}