package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.ComplaintRequestDTO;
import com.pms.political_management_system.dto.response.ComplaintResponseDTO;
import com.pms.political_management_system.service.ComplaintService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    // Get All Complaints
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<ComplaintResponseDTO> getAllComplaints() {
        return complaintService.getAllComplaints();
    }

    // Get Complaint By Id
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public ComplaintResponseDTO getComplaintById(@PathVariable Long id) {
        return complaintService.getComplaintById(id);
    }

    // Create Complaint
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ComplaintResponseDTO saveComplaint(
            @Valid @RequestBody ComplaintRequestDTO requestDTO) {

        return complaintService.saveComplaint(requestDTO);
    }

    // Update Complaint
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public ComplaintResponseDTO updateComplaint(
            @PathVariable Long id,
            @Valid @RequestBody ComplaintRequestDTO requestDTO) {

        return complaintService.updateComplaint(id, requestDTO);
    }

    // Delete Complaint
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public String deleteComplaint(@PathVariable Long id) {
        return complaintService.deleteComplaint(id);
    }
}