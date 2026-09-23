package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.EventAttendanceRequestDTO;
import com.pms.political_management_system.dto.response.EventAttendanceResponseDTO;
import com.pms.political_management_system.service.EventAttendanceService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event-attendance")
public class EventAttendanceController {

    @Autowired
    private EventAttendanceService attendanceService;

    // Get All Attendance
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<EventAttendanceResponseDTO> getAllAttendance() {
        return attendanceService.getAllAttendance();
    }

    // Get Attendance By Id
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public EventAttendanceResponseDTO getAttendanceById(@PathVariable Long id) {
        return attendanceService.getAttendanceById(id);
    }

    // Create Attendance
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public EventAttendanceResponseDTO saveAttendance(
            @Valid @RequestBody EventAttendanceRequestDTO requestDTO) {

        return attendanceService.saveAttendance(requestDTO);
    }

    // Update Attendance
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public EventAttendanceResponseDTO updateAttendance(
            @PathVariable Long id,
            @Valid @RequestBody EventAttendanceRequestDTO requestDTO) {

        return attendanceService.updateAttendance(id, requestDTO);
    }

    // Delete Attendance
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public String deleteAttendance(@PathVariable Long id) {
        return attendanceService.deleteAttendance(id);
    }
}