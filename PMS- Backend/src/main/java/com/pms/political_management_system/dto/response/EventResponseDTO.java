package com.pms.political_management_system.dto.response;

import java.time.LocalDate;

public class EventResponseDTO {

    private Long id;
    private String eventName;
    private String eventType;
    private String venue;
    private String description;
    private LocalDate eventDate;

    private Long campaignId;
    private String campaignName;

    public EventResponseDTO() {
    }

    public EventResponseDTO(Long id,
                            String eventName,
                            String eventType,
                            String venue,
                            String description,
                            LocalDate eventDate,
                            Long campaignId,
                            String campaignName) {
        this.id = id;
        this.eventName = eventName;
        this.eventType = eventType;
        this.venue = venue;
        this.description = description;
        this.eventDate = eventDate;
        this.campaignId = campaignId;
        this.campaignName = campaignName;
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

    public Long getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(Long campaignId) {
        this.campaignId = campaignId;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public void setCampaignName(String campaignName) {
        this.campaignName = campaignName;
    }
}