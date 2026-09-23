package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.ConstituencyRequestDTO;
import com.pms.political_management_system.dto.response.ConstituencyResponseDTO;
import com.pms.political_management_system.entity.Constituency;
import com.pms.political_management_system.entity.District;
import com.pms.political_management_system.entity.Party;
import com.pms.political_management_system.repository.ConstituencyRepository;
import com.pms.political_management_system.repository.DistrictRepository;
import com.pms.political_management_system.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConstituencyService {

    @Autowired
    private ConstituencyRepository constituencyRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private DistrictRepository districtRepository;

    // Get All
    public List<ConstituencyResponseDTO> getAllConstituencies() {

        return constituencyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save
    public ConstituencyResponseDTO saveConstituency(ConstituencyRequestDTO requestDTO) {

        if (requestDTO.getPartyId() == null) {
            throw new RuntimeException("Party is required");
        }

        if (requestDTO.getDistrictId() == null) {
            throw new RuntimeException("District is required");
        }

        Party party = partyRepository.findById(requestDTO.getPartyId())
                .orElseThrow(() -> new RuntimeException("Party Not Found"));

        District district = districtRepository.findById(requestDTO.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District Not Found"));

        Constituency constituency = new Constituency();

        constituency.setConstituencyName(requestDTO.getConstituencyName());
        constituency.setDistrict(district);
        constituency.setParty(party);

        Constituency saved = constituencyRepository.save(constituency);

        return mapToResponse(saved);
    }

    // Get By ID
    public ConstituencyResponseDTO getConstituencyById(Long id) {

        Constituency c = constituencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Constituency Not Found"));

        return mapToResponse(c);
    }

    // Update
    public ConstituencyResponseDTO updateConstituency(Long id,
                                                      ConstituencyRequestDTO requestDTO) {

        Constituency constituency = constituencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Constituency Not Found"));

        if (requestDTO.getPartyId() == null) {
            throw new RuntimeException("Party is required");
        }

        if (requestDTO.getDistrictId() == null) {
            throw new RuntimeException("District is required");
        }

        Party party = partyRepository.findById(requestDTO.getPartyId())
                .orElseThrow(() -> new RuntimeException("Party Not Found"));

        District district = districtRepository.findById(requestDTO.getDistrictId())
                .orElseThrow(() -> new RuntimeException("District Not Found"));

        constituency.setConstituencyName(requestDTO.getConstituencyName());
        constituency.setDistrict(district);
        constituency.setParty(party);

        Constituency updated = constituencyRepository.save(constituency);

        return mapToResponse(updated);
    }

    // Delete
    public String deleteConstituency(Long id) {

        Constituency constituency = constituencyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Constituency Not Found"));

        constituencyRepository.delete(constituency);

        return "Constituency Deleted Successfully";
    }

    // Mapping Method (null-safe against a missing district/state/party link)
    private ConstituencyResponseDTO mapToResponse(Constituency c) {

        District district = c.getDistrict();

        return new ConstituencyResponseDTO(
                c.getId(),
                c.getConstituencyName(),

                district != null ? district.getId() : null,
                district != null ? district.getDistrictName() : null,

                district != null && district.getState() != null ? district.getState().getId() : null,
                district != null && district.getState() != null ? district.getState().getStateName() : null,

                c.getParty() != null ? c.getParty().getId() : null,
                c.getParty() != null ? c.getParty().getPartyName() : null
        );
    }
}
