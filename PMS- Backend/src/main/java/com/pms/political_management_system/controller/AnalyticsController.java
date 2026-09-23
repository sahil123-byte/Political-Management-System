package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.response.AnalyticsResponseDTO;
import com.pms.political_management_system.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public AnalyticsResponseDTO getAnalytics() {
        return analyticsService.getAnalytics();
    }
}