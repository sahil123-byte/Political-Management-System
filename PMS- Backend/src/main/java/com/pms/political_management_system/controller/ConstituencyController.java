package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.ConstituencyRequestDTO;
import com.pms.political_management_system.dto.response.ConstituencyResponseDTO;
import com.pms.political_management_system.service.ConstituencyService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/constituencies")
public class ConstituencyController {

    @Autowired
    private ConstituencyService constituencyService;

    // Get All Constituencies
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public List<ConstituencyResponseDTO> getAllConstituencies() {
        return constituencyService.getAllConstituencies();
    }

    // Get Constituency By ID
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/{id}")
    public ConstituencyResponseDTO getConstituencyById(@PathVariable Long id) {
        return constituencyService.getConstituencyById(id);
    }

    // Create Constituency
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ConstituencyResponseDTO saveConstituency(
            @Valid @RequestBody ConstituencyRequestDTO requestDTO) {

        return constituencyService.saveConstituency(requestDTO);
    }

    // Update Constituency
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ConstituencyResponseDTO updateConstituency(
            @PathVariable Long id,
            @Valid @RequestBody ConstituencyRequestDTO requestDTO) {

        return constituencyService.updateConstituency(id, requestDTO);
    }

    // Delete Constituency
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteConstituency(@PathVariable Long id) {
        return constituencyService.deleteConstituency(id);
    }
}