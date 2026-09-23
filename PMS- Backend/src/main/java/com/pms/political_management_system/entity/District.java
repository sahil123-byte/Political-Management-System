package com.pms.political_management_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "districts")
public class District {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String districtName;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    public District() {
    }

    public District(Long id, String districtName, State state) {
        this.id = id;
        this.districtName = districtName;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public String getDistrictName() {
        return districtName;
    }

    public State getState() {
        return state;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public void setState(State state) {
        this.state = state;
    }
}