package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.CampaignRequestDTO;
import com.pms.political_management_system.dto.response.CampaignResponseDTO;
import com.pms.political_management_system.service.CampaignService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
public class CampaignController {

    @Autowired
    private CampaignService campaignService;

    // Get All Campaigns
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<CampaignResponseDTO> getAllCampaigns() {
        return campaignService.getAllCampaigns();
    }

    // Get Campaign By Id
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public CampaignResponseDTO getCampaignById(@PathVariable Long id) {
        return campaignService.getCampaignById(id);
    }

    // Create Campaign
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public CampaignResponseDTO saveCampaign(
            @Valid @RequestBody CampaignRequestDTO requestDTO) {

        return campaignService.saveCampaign(requestDTO);
    }

    // Update Campaign
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public CampaignResponseDTO updateCampaign(
            @PathVariable Long id,
            @Valid @RequestBody CampaignRequestDTO requestDTO) {

        return campaignService.updateCampaign(id, requestDTO);
    }

    // Delete Campaign
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public String deleteCampaign(@PathVariable Long id) {
        return campaignService.deleteCampaign(id);
    }
}