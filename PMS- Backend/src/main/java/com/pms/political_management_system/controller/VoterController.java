package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.VoterRequestDTO;
import com.pms.political_management_system.dto.response.VoterResponseDTO;
import com.pms.political_management_system.service.VoterService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voters")
public class VoterController {

    @Autowired
    private VoterService voterService;

    // Get All Voters
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<VoterResponseDTO> getAllVoters() {
        return voterService.getAllVoters();
    }

    // Get Voter By ID
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public VoterResponseDTO getVoterById(@PathVariable Long id) {
        return voterService.getVoterById(id);
    }

    // Create Voter
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public VoterResponseDTO saveVoter(
            @Valid @RequestBody VoterRequestDTO requestDTO) {

        return voterService.saveVoter(requestDTO);
    }

    // Update Voter
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public VoterResponseDTO updateVoter(
            @PathVariable Long id,
            @Valid @RequestBody VoterRequestDTO requestDTO) {

        return voterService.updateVoter(id, requestDTO);
    }

    // Delete Voter
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public String deleteVoter(@PathVariable Long id) {
        return voterService.deleteVoter(id);
    }
}