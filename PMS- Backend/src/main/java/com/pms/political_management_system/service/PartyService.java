package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.PartyRequestDTO;
import com.pms.political_management_system.dto.response.PartyResponseDTO;
import com.pms.political_management_system.entity.Party;
import com.pms.political_management_system.repository.PartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartyService {

    @Autowired
    private PartyRepository partyRepository;

    // Get All Parties
    public List<PartyResponseDTO> getAllParties() {

        return partyRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save Party
    public PartyResponseDTO saveParty(PartyRequestDTO requestDTO) {

        Party party = new Party();

        party.setPartyName(requestDTO.getPartyName());
        party.setPartySymbol(requestDTO.getPartySymbol());
        party.setPartyPresident(requestDTO.getPartyPresident());
        party.setDescription(requestDTO.getDescription());
        party.setLogoUrl(requestDTO.getLogoUrl());

        Party savedParty = partyRepository.save(party);

        return mapToResponse(savedParty);
    }

    // Get Party By ID
    public PartyResponseDTO getPartyById(Long id) {

        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Party Not Found"));

        return mapToResponse(party);
    }

    // Update Party
    public PartyResponseDTO updateParty(Long id, PartyRequestDTO requestDTO) {

        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Party Not Found"));

        party.setPartyName(requestDTO.getPartyName());
        party.setPartySymbol(requestDTO.getPartySymbol());
        party.setPartyPresident(requestDTO.getPartyPresident());
        party.setDescription(requestDTO.getDescription());
        party.setLogoUrl(requestDTO.getLogoUrl());

        Party updatedParty = partyRepository.save(party);

        return mapToResponse(updatedParty);
    }

    // Delete Party
    public String deleteParty(Long id) {

        Party party = partyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Party Not Found"));

        partyRepository.delete(party);

        return "Party Deleted Successfully";
    }

    // Mapping Method
    private PartyResponseDTO mapToResponse(Party party) {

        return new PartyResponseDTO(
                party.getId(),
                party.getPartyName(),
                party.getPartySymbol(),
                party.getPartyPresident(),
                party.getDescription(),
                party.getLogoUrl()
        );
    }
}
