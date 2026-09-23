package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportsService {

    @Autowired
    private MemberService memberService;

    @Autowired
    private VoterService voterService;

    @Autowired
    private CampaignService campaignService;

    @Autowired
    private EventService eventService;

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private OrganizationService organizationService;

    // Member Report
    public List<MemberResponseDTO> getMemberReport() {
        return memberService.getAllMembers();
    }

    // Voter Report
    public List<VoterResponseDTO> getVoterReport() {
        return voterService.getAllVoters();
    }

    // Campaign Report
    public List<CampaignResponseDTO> getCampaignReport() {
        return campaignService.getAllCampaigns();
    }

    // Event Report
    public List<EventResponseDTO> getEventReport() {
        return eventService.getAllEvents();
    }

    // Complaint Report
    public List<ComplaintResponseDTO> getComplaintReport() {
        return complaintService.getAllComplaints();
    }

    // Feedback Report
    public List<FeedbackResponseDTO> getFeedbackReport() {
        return feedbackService.getAllFeedbacks();
    }

    // Organization Report
    public List<OrganizationResponseDTO> getOrganizationReport() {
        return organizationService.getAllOrganizations();
    }
}