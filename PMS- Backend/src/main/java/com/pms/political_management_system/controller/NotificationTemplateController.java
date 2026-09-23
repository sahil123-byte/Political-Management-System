package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.NotificationTemplateRequestDTO;
import com.pms.political_management_system.dto.response.NotificationTemplateResponseDTO;
import com.pms.political_management_system.service.NotificationTemplateService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification-templates")
public class NotificationTemplateController {

    private final NotificationTemplateService templateService;

    public NotificationTemplateController(NotificationTemplateService templateService) {
        this.templateService = templateService;
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public List<NotificationTemplateResponseDTO> getAllTemplates() {
        return templateService.getAllTemplates();
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/{id}")
    public NotificationTemplateResponseDTO getTemplateById(@PathVariable Long id) {
        return templateService.getTemplateById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public NotificationTemplateResponseDTO saveTemplate(@Valid @RequestBody NotificationTemplateRequestDTO requestDTO) {
        return templateService.saveTemplate(requestDTO);
    }

    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public NotificationTemplateResponseDTO updateTemplate(@PathVariable Long id,
                                                           @Valid @RequestBody NotificationTemplateRequestDTO requestDTO) {
        return templateService.updateTemplate(id, requestDTO);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        return "Template Deleted Successfully";
    }
}
