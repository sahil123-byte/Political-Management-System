package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.DistrictRequestDTO;
import com.pms.political_management_system.dto.response.DistrictResponseDTO;
import com.pms.political_management_system.service.DistrictService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {

    @Autowired
    private DistrictService districtService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public List<DistrictResponseDTO> getAllDistricts() {
        return districtService.getAllDistricts();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public DistrictResponseDTO saveDistrict(
            @Valid @RequestBody DistrictRequestDTO requestDTO) {

        return districtService.saveDistrict(requestDTO);
    }
}