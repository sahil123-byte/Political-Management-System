package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.EventAttendanceRequestDTO;
import com.pms.political_management_system.dto.response.EventAttendanceResponseDTO;
import com.pms.political_management_system.entity.Event;
import com.pms.political_management_system.entity.EventAttendance;
import com.pms.political_management_system.entity.Member;
import com.pms.political_management_system.repository.EventAttendanceRepository;
import com.pms.political_management_system.repository.EventRepository;
import com.pms.political_management_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventAttendanceService {

    @Autowired
    private EventAttendanceRepository attendanceRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private MemberRepository memberRepository;

    // Get All Attendance
    public List<EventAttendanceResponseDTO> getAllAttendance() {

        return attendanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save Attendance
    public EventAttendanceResponseDTO saveAttendance(EventAttendanceRequestDTO requestDTO) {

        if (requestDTO.getEventId() == null) {
            throw new RuntimeException("Event is required");
        }

        if (requestDTO.getMemberId() == null) {
            throw new RuntimeException("Member is required");
        }

        Event event = eventRepository.findById(requestDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Event Not Found"));

        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

        EventAttendance attendance = new EventAttendance();

        attendance.setEvent(event);
        attendance.setMember(member);
        attendance.setAttendanceStatus(requestDTO.getAttendanceStatus());
        attendance.setRemarks(requestDTO.getRemarks());

        EventAttendance saved = attendanceRepository.save(attendance);

        return mapToResponse(saved);
    }

    // Get Attendance By Id
    public EventAttendanceResponseDTO getAttendanceById(Long id) {

        EventAttendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance Not Found"));

        return mapToResponse(attendance);
    }

    // Update Attendance
    public EventAttendanceResponseDTO updateAttendance(Long id,
                                                       EventAttendanceRequestDTO requestDTO) {

        EventAttendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance Not Found"));

        if (requestDTO.getEventId() == null) {
            throw new RuntimeException("Event is required");
        }

        if (requestDTO.getMemberId() == null) {
            throw new RuntimeException("Member is required");
        }

        Event event = eventRepository.findById(requestDTO.getEventId())
                .orElseThrow(() -> new RuntimeException("Event Not Found"));

        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

        attendance.setEvent(event);
        attendance.setMember(member);
        attendance.setAttendanceStatus(requestDTO.getAttendanceStatus());
        attendance.setRemarks(requestDTO.getRemarks());

        EventAttendance updated = attendanceRepository.save(attendance);

        return mapToResponse(updated);
    }

    // Delete Attendance
    public String deleteAttendance(Long id) {

        EventAttendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Attendance Not Found"));

        attendanceRepository.delete(attendance);

        return "Attendance Deleted Successfully";
    }

    // Mapping Method (null-safe against a missing/deleted event or member link)
    private EventAttendanceResponseDTO mapToResponse(EventAttendance a) {

        return new EventAttendanceResponseDTO(
                a.getId(),
                a.getEvent() != null ? a.getEvent().getId() : null,
                a.getEvent() != null ? a.getEvent().getEventName() : null,
                a.getMember() != null ? a.getMember().getId() : null,
                a.getMember() != null ? a.getMember().getName() : null,
                a.getAttendanceStatus(),
                a.getRemarks()
        );
    }
}
