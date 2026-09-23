package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.EventRequestDTO;
import com.pms.political_management_system.dto.response.EventResponseDTO;
import com.pms.political_management_system.entity.Campaign;
import com.pms.political_management_system.entity.Event;
import com.pms.political_management_system.repository.CampaignRepository;
import com.pms.political_management_system.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    // Get All Events
    public List<EventResponseDTO> getAllEvents() {

        return eventRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save Event
    public EventResponseDTO saveEvent(EventRequestDTO requestDTO) {

        if (requestDTO.getCampaignId() == null) {
            throw new RuntimeException("Campaign is required");
        }

        Campaign campaign = campaignRepository.findById(requestDTO.getCampaignId())
                .orElseThrow(() -> new RuntimeException("Campaign Not Found"));

        Event event = new Event();

        event.setEventName(requestDTO.getEventName());
        event.setEventType(requestDTO.getEventType());
        event.setVenue(requestDTO.getVenue());
        event.setDescription(requestDTO.getDescription());
        event.setEventDate(requestDTO.getEventDate());
        event.setCampaign(campaign);

        Event saved = eventRepository.save(event);

        return mapToResponse(saved);
    }

    // Get Event By Id
    public EventResponseDTO getEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event Not Found"));

        return mapToResponse(event);
    }

    // Update Event
    public EventResponseDTO updateEvent(Long id, EventRequestDTO requestDTO) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event Not Found"));

        if (requestDTO.getCampaignId() == null) {
            throw new RuntimeException("Campaign is required");
        }

        Campaign campaign = campaignRepository.findById(requestDTO.getCampaignId())
                .orElseThrow(() -> new RuntimeException("Campaign Not Found"));

        event.setEventName(requestDTO.getEventName());
        event.setEventType(requestDTO.getEventType());
        event.setVenue(requestDTO.getVenue());
        event.setDescription(requestDTO.getDescription());
        event.setEventDate(requestDTO.getEventDate());
        event.setCampaign(campaign);

        Event updated = eventRepository.save(event);

        return mapToResponse(updated);
    }

    // Delete Event
    public String deleteEvent(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event Not Found"));

        eventRepository.delete(event);

        return "Event Deleted Successfully";
    }

    // Mapping Method (null-safe against a missing/deleted campaign link)
    private EventResponseDTO mapToResponse(Event event) {

        return new EventResponseDTO(
                event.getId(),
                event.getEventName(),
                event.getEventType(),
                event.getVenue(),
                event.getDescription(),
                event.getEventDate(),
                event.getCampaign() != null ? event.getCampaign().getId() : null,
                event.getCampaign() != null ? event.getCampaign().getCampaignName() : null
        );
    }
}
