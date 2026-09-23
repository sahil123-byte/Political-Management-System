package com.pms.political_management_system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String eventName;

    private String eventType;

    @Column(nullable = false)
    private String venue;

    @Column(length = 1000)
    private String description;

    private LocalDate eventDate;

    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;

    public Event() {
    }

    public Event(Long id, String eventName, String eventType,
                 String venue, String description,
                 LocalDate eventDate, Campaign campaign) {
        this.id = id;
        this.eventName = eventName;
        this.eventType = eventType;
        this.venue = venue;
        this.description = description;
        this.eventDate = eventDate;
        this.campaign = campaign;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }
}