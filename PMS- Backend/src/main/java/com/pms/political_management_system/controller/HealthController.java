package com.pms.political_management_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping
    public Map<String, String> status() {
        return Map.of("status", "ok", "service", "political-management-system");
    }
}
