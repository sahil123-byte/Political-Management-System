package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.CampaignRequestDTO;
import com.pms.political_management_system.dto.response.CampaignResponseDTO;
import com.pms.political_management_system.entity.Campaign;
import com.pms.political_management_system.entity.Party;
import com.pms.political_management_system.repository.CampaignRepository;
import com.pms.political_management_system.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private PartyRepository partyRepository;

    // Get All Campaigns
    public List<CampaignResponseDTO> getAllCampaigns() {

        return campaignRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get Campaign By Id
    public CampaignResponseDTO getCampaignById(Long id) {

        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign Not Found"));

        return mapToResponse(campaign);
    }

    // Save Campaign
    public CampaignResponseDTO saveCampaign(CampaignRequestDTO requestDTO) {

        if (requestDTO.getPartyId() == null) {
            throw new RuntimeException("Party is required");
        }

        Party party = partyRepository.findById(requestDTO.getPartyId())
                .orElseThrow(() -> new RuntimeException("Party Not Found"));

        Campaign campaign = new Campaign();

        campaign.setCampaignName(requestDTO.getCampaignName());
        campaign.setDescription(requestDTO.getDescription());
        campaign.setStartDate(requestDTO.getStartDate());
        campaign.setEndDate(requestDTO.getEndDate());
        campaign.setBudget(requestDTO.getBudget());
        campaign.setParty(party);

        Campaign saved = campaignRepository.save(campaign);

        return mapToResponse(saved);
    }

    // Update Campaign
    public CampaignResponseDTO updateCampaign(Long id,
                                              CampaignRequestDTO requestDTO) {

        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign Not Found"));

        if (requestDTO.getPartyId() == null) {
            throw new RuntimeException("Party is required");
        }

        Party party = partyRepository.findById(requestDTO.getPartyId())
                .orElseThrow(() -> new RuntimeException("Party Not Found"));

        campaign.setCampaignName(requestDTO.getCampaignName());
        campaign.setDescription(requestDTO.getDescription());
        campaign.setStartDate(requestDTO.getStartDate());
        campaign.setEndDate(requestDTO.getEndDate());
        campaign.setBudget(requestDTO.getBudget());
        campaign.setParty(party);

        Campaign updated = campaignRepository.save(campaign);

        return mapToResponse(updated);
    }

    // Delete Campaign
    public String deleteCampaign(Long id) {

        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign Not Found"));

        campaignRepository.delete(campaign);

        return "Campaign Deleted Successfully";
    }

    // Mapping Method
    private CampaignResponseDTO mapToResponse(Campaign campaign) {

        return new CampaignResponseDTO(

                campaign.getId(),
                campaign.getCampaignName(),
                campaign.getDescription(),
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getBudget(),
                campaign.getStatus(),

                campaign.getParty() != null ? campaign.getParty().getId() : null,
                campaign.getParty() != null ? campaign.getParty().getPartyName() : null,

                campaign.getCreatedAt(),
                campaign.getUpdatedAt()

        );
    }
}