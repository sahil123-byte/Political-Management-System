package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.EventRequestDTO;
import com.pms.political_management_system.dto.response.EventResponseDTO;
import com.pms.political_management_system.service.EventService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    // Get All Events
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<EventResponseDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Get Event By Id
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public EventResponseDTO getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    // Create Event
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PostMapping
    public EventResponseDTO saveEvent(
            @Valid @RequestBody EventRequestDTO requestDTO) {

        return eventService.saveEvent(requestDTO);
    }

    // Update Event
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public EventResponseDTO updateEvent(
            @PathVariable Long id,
            @Valid @RequestBody EventRequestDTO requestDTO) {

        return eventService.updateEvent(id, requestDTO);
    }

    // Delete Event
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public String deleteEvent(@PathVariable Long id) {
        return eventService.deleteEvent(id);
    }
}