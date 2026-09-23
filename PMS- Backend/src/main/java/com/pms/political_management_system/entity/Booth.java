package com.pms.political_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "booths")
public class Booth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String boothNumber;

    @Column(nullable = false)
    private String boothName;

    private String location;

    @ManyToOne
    @JoinColumn(name = "constituency_id")
    private Constituency constituency;

    public Booth() {
    }

    public Booth(Long id, String boothNumber, String boothName, String location, Constituency constituency) {
        this.id = id;
        this.boothNumber = boothNumber;
        this.boothName = boothName;
        this.location = location;
        this.constituency = constituency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoothNumber() {
        return boothNumber;
    }

    public void setBoothNumber(String boothNumber) {
        this.boothNumber = boothNumber;
    }

    public String getBoothName() {
        return boothName;
    }

    public void setBoothName(String boothName) {
        this.boothName = boothName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Constituency getConstituency() {
        return constituency;
    }

    public void setConstituency(Constituency constituency) {
        this.constituency = constituency;
    }
}