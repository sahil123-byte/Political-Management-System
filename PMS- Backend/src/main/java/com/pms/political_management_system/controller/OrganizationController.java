package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.OrganizationRequestDTO;
import com.pms.political_management_system.dto.response.OrganizationResponseDTO;
import com.pms.political_management_system.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    // Get All Organizations
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public List<OrganizationResponseDTO> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    // Get Organization By Id
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/{id}")
    public OrganizationResponseDTO getOrganizationById(@PathVariable Long id) {
        return organizationService.getOrganizationById(id);
    }

    // Create Organization
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public OrganizationResponseDTO saveOrganization(
            @Valid @RequestBody OrganizationRequestDTO requestDTO) {

        return organizationService.saveOrganization(requestDTO);
    }

    // Update Organization
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public OrganizationResponseDTO updateOrganization(
            @PathVariable Long id,
            @Valid @RequestBody OrganizationRequestDTO requestDTO) {

        return organizationService.updateOrganization(id, requestDTO);
    }

    // Delete Organization
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public String deleteOrganization(@PathVariable Long id) {
        return organizationService.deleteOrganization(id);
    }
}