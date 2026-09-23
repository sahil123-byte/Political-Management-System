package com.pms.political_management_system.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CampaignResponseDTO {

    private Long id;
    private String campaignName;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal budget;
    private Boolean status;
    private Long partyId;
    private String partyName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CampaignResponseDTO() {
    }

    public CampaignResponseDTO(Long id,
                               String campaignName,
                               String description,
                               LocalDate startDate,
                               LocalDate endDate,
                               BigDecimal budget,
                               Boolean status,
                               Long partyId,
                               String partyName,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt) {

        this.id = id;
        this.campaignName = campaignName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.budget = budget;
        this.status = status;
        this.partyId = partyId;
        this.partyName = partyName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public String getCampaignName() {
        return campaignName;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public Boolean getStatus() {
        return status;
    }

    public Long getPartyId() {
        return partyId;
    }

    public String getPartyName() {
        return partyName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}