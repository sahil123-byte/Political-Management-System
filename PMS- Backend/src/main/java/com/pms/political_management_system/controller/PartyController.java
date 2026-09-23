package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.PartyRequestDTO;
import com.pms.political_management_system.dto.response.PartyResponseDTO;
import com.pms.political_management_system.service.PartyService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parties")
public class PartyController {

    @Autowired
    private PartyService partyService;

    // Get All Parties
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public List<PartyResponseDTO> getAllParties() {
        return partyService.getAllParties();
    }

    // Get Party By ID
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/{id}")
    public PartyResponseDTO getPartyById(@PathVariable Long id) {
        return partyService.getPartyById(id);
    }

    // Create Party
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public PartyResponseDTO saveParty(@Valid @RequestBody PartyRequestDTO requestDTO) {
        return partyService.saveParty(requestDTO);
    }

    // Update Party
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public PartyResponseDTO updateParty(@PathVariable Long id,
                                        @Valid @RequestBody PartyRequestDTO requestDTO) {
        return partyService.updateParty(id, requestDTO);
    }

    // Delete Party
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteParty(@PathVariable Long id) {
        return partyService.deleteParty(id);
    }
}