package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.StateRequestDTO;
import com.pms.political_management_system.dto.response.StateResponseDTO;
import com.pms.political_management_system.service.StateService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public List<StateResponseDTO> getAllStates() {
        return stateService.getAllStates();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public StateResponseDTO saveState(
            @Valid @RequestBody StateRequestDTO requestDTO) {

        return stateService.saveState(requestDTO);
    }
}