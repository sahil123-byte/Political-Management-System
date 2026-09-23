package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.response.*;
import com.pms.political_management_system.service.ReportsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/reports")
public class ReportsController {

    @Autowired
    private ReportsService reportsService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/members")
    public List<MemberResponseDTO> getMemberReport() {
        return reportsService.getMemberReport();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/voters")
    public List<VoterResponseDTO> getVoterReport() {
        return reportsService.getVoterReport();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/campaigns")
    public List<CampaignResponseDTO> getCampaignReport() {
        return reportsService.getCampaignReport();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/events")
    public List<EventResponseDTO> getEventReport() {
        return reportsService.getEventReport();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/complaints")
    public List<ComplaintResponseDTO> getComplaintReport() {
        return reportsService.getComplaintReport();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/feedbacks")
    public List<FeedbackResponseDTO> getFeedbackReport() {
        return reportsService.getFeedbackReport();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/organizations")
    public List<OrganizationResponseDTO> getOrganizationReport() {
        return reportsService.getOrganizationReport();
    }
}