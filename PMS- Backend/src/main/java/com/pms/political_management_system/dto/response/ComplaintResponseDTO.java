package com.pms.political_management_system.dto.response;

import java.time.LocalDate;

public class ComplaintResponseDTO {

    private Long id;

    private String complaintTitle;

    private String complaintDescription;

    private String complaintStatus;

    private LocalDate complaintDate;

    private Long memberId;

    private String memberName;

    public ComplaintResponseDTO() {
    }

    public ComplaintResponseDTO(Long id,
                                String complaintTitle,
                                String complaintDescription,
                                String complaintStatus,
                                LocalDate complaintDate,
                                Long memberId,
                                String memberName) {
        this.id = id;
        this.complaintTitle = complaintTitle;
        this.complaintDescription = complaintDescription;
        this.complaintStatus = complaintStatus;
        this.complaintDate = complaintDate;
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}