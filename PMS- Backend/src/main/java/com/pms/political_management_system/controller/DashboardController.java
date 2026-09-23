package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.response.DashboardResponseDTO;
import com.pms.political_management_system.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public DashboardResponseDTO getDashboardData() {
        return dashboardService.getDashboardData();
    }
}