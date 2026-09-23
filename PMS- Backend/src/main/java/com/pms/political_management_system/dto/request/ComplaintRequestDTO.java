package com.pms.political_management_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class ComplaintRequestDTO {

    @NotBlank(message = "Complaint Title is required")
    private String complaintTitle;

    @NotBlank(message = "Complaint Description is required")
    private String complaintDescription;

    @NotBlank(message = "Complaint Status is required")
    private String complaintStatus;

    @NotNull(message = "Complaint Date is required")
    private LocalDate complaintDate;

    @NotNull(message = "Member Id is required")
    private Long memberId;

    public ComplaintRequestDTO() {
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        this.complaintTitle = complaintTitle;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public LocalDate getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(LocalDate complaintDate) {
        this.complaintDate = complaintDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}