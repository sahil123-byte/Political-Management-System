package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.BoothRequestDTO;
import com.pms.political_management_system.dto.response.BoothResponseDTO;
import com.pms.political_management_system.service.BoothService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booths")
public class BoothController {

    @Autowired
    private BoothService boothService;

    // Get All Booths
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public List<BoothResponseDTO> getAllBooths() {
        return boothService.getAllBooths();
    }

    // Get Booth By ID
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/{id}")
    public BoothResponseDTO getBoothById(@PathVariable Long id) {
        return boothService.getBoothById(id);
    }

    // Create Booth
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public BoothResponseDTO saveBooth(
            @Valid @RequestBody BoothRequestDTO requestDTO) {

        return boothService.saveBooth(requestDTO);
    }

    // Update Booth
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public BoothResponseDTO updateBooth(
            @PathVariable Long id,
            @Valid @RequestBody BoothRequestDTO requestDTO) {

        return boothService.updateBooth(id, requestDTO);
    }

    // Delete Booth
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public String deleteBooth(@PathVariable Long id) {
        return boothService.deleteBooth(id);
    }
}