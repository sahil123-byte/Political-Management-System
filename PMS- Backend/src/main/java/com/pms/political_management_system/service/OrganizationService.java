package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.OrganizationRequestDTO;
import com.pms.political_management_system.dto.response.OrganizationResponseDTO;
import com.pms.political_management_system.entity.Organization;
import com.pms.political_management_system.entity.Party;
import com.pms.political_management_system.repository.OrganizationRepository;
import com.pms.political_management_system.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private PartyRepository partyRepository;

    // Get All Organizations
    public List<OrganizationResponseDTO> getAllOrganizations() {

        return organizationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save Organization
    public OrganizationResponseDTO saveOrganization(OrganizationRequestDTO requestDTO) {

        if (requestDTO.getPartyId() == null) {
            throw new RuntimeException("Party is required");
        }

        Party party = partyRepository.findById(requestDTO.getPartyId())
                .orElseThrow(() -> new RuntimeException("Party Not Found"));

        Organization organization = new Organization();

        organization.setOrganizationName(requestDTO.getOrganizationName());
        organization.setOrganizationType(requestDTO.getOrganizationType());
        organization.setHeadName(requestDTO.getHeadName());
        organization.setContactNumber(requestDTO.getContactNumber());
        organization.setEmail(requestDTO.getEmail());
        organization.setAddress(requestDTO.getAddress());
        organization.setParty(party);

        Organization saved = organizationRepository.save(organization);

        return mapToResponse(saved);
    }

    // Get Organization By Id
    public OrganizationResponseDTO getOrganizationById(Long id) {

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization Not Found"));

        return mapToResponse(organization);
    }

    // Update Organization
    public OrganizationResponseDTO updateOrganization(Long id,
                                                      OrganizationRequestDTO requestDTO) {

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization Not Found"));

        if (requestDTO.getPartyId() == null) {
            throw new RuntimeException("Party is required");
        }

        Party party = partyRepository.findById(requestDTO.getPartyId())
                .orElseThrow(() -> new RuntimeException("Party Not Found"));

        organization.setOrganizationName(requestDTO.getOrganizationName());
        organization.setOrganizationType(requestDTO.getOrganizationType());
        organization.setHeadName(requestDTO.getHeadName());
        organization.setContactNumber(requestDTO.getContactNumber());
        organization.setEmail(requestDTO.getEmail());
        organization.setAddress(requestDTO.getAddress());
        organization.setParty(party);

        Organization updated = organizationRepository.save(organization);

        return mapToResponse(updated);
    }

    // Delete Organization
    public String deleteOrganization(Long id) {

        Organization organization = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization Not Found"));

        organizationRepository.delete(organization);

        return "Organization Deleted Successfully";
    }

    // Mapping Method (null-safe against a missing/deleted party link)
    private OrganizationResponseDTO mapToResponse(Organization o) {

        return new OrganizationResponseDTO(
                o.getId(),
                o.getOrganizationName(),
                o.getOrganizationType(),
                o.getHeadName(),
                o.getContactNumber(),
                o.getEmail(),
                o.getAddress(),
                o.getParty() != null ? o.getParty().getId() : null,
                o.getParty() != null ? o.getParty().getPartyName() : null
        );
    }
}
