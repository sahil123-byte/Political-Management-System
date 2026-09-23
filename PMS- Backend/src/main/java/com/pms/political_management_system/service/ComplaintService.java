package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.ComplaintRequestDTO;
import com.pms.political_management_system.dto.response.ComplaintResponseDTO;
import com.pms.political_management_system.entity.Complaint;
import com.pms.political_management_system.entity.Member;
import com.pms.political_management_system.repository.ComplaintRepository;
import com.pms.political_management_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CurrentUserService currentUserService;

    // Get All Complaints
    public List<ComplaintResponseDTO> getAllComplaints() {

        // MEMBER role: "Own" access - only see complaints filed for their own Member record.
        if (currentUserService.isMember()) {

            Long memberId = currentUserService.getCurrentMemberId();

            if (memberId == null) {
                return List.of();
            }

            return complaintRepository.findAll()
                    .stream()
                    .filter(c -> c.getMember() != null && memberId.equals(c.getMember().getId()))
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        }

        return complaintRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save Complaint
    public ComplaintResponseDTO saveComplaint(ComplaintRequestDTO requestDTO) {

        Long memberId = requestDTO.getMemberId();

        // MEMBER role: always file the complaint under their own Member record,
        // regardless of what memberId the client sent (prevents impersonation).
        if (currentUserService.isMember()) {
            memberId = currentUserService.getCurrentMemberId();

            if (memberId == null) {
                throw new RuntimeException("Your account is not linked to a Member record");
            }
        }

        if (memberId == null) {
            throw new RuntimeException("Member is required");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

        Complaint complaint = new Complaint();

        complaint.setComplaintTitle(requestDTO.getComplaintTitle());
        complaint.setComplaintDescription(requestDTO.getComplaintDescription());
        complaint.setComplaintStatus(requestDTO.getComplaintStatus());
        complaint.setComplaintDate(requestDTO.getComplaintDate());
        complaint.setMember(member);

        Complaint saved = complaintRepository.save(complaint);

        return mapToResponse(saved);
    }

    // Get Complaint By Id
    public ComplaintResponseDTO getComplaintById(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint Not Found"));

        assertOwnComplaintIfMember(complaint);

        return mapToResponse(complaint);
    }

    // Update Complaint
    public ComplaintResponseDTO updateComplaint(Long id,
                                                ComplaintRequestDTO requestDTO) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint Not Found"));

        if (requestDTO.getMemberId() == null) {
            throw new RuntimeException("Member is required");
        }

        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

        complaint.setComplaintTitle(requestDTO.getComplaintTitle());
        complaint.setComplaintDescription(requestDTO.getComplaintDescription());
        complaint.setComplaintStatus(requestDTO.getComplaintStatus());
        complaint.setComplaintDate(requestDTO.getComplaintDate());
        complaint.setMember(member);

        Complaint updated = complaintRepository.save(complaint);

        return mapToResponse(updated);
    }

    // Delete Complaint
    public String deleteComplaint(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint Not Found"));

        complaintRepository.delete(complaint);

        return "Complaint Deleted Successfully";
    }

    // Defends against a MEMBER user guessing another member's complaint ID
    // via GET /api/complaints/{id}
    private void assertOwnComplaintIfMember(Complaint complaint) {

        if (!currentUserService.isMember()) {
            return;
        }

        Long memberId = currentUserService.getCurrentMemberId();

        if (complaint.getMember() == null || !complaint.getMember().getId().equals(memberId)) {
            throw new RuntimeException("You are not allowed to view this complaint");
        }
    }

    // Mapping Method (null-safe against a missing/deleted member link)
    private ComplaintResponseDTO mapToResponse(Complaint c) {

        return new ComplaintResponseDTO(
                c.getId(),
                c.getComplaintTitle(),
                c.getComplaintDescription(),
                c.getComplaintStatus(),
                c.getComplaintDate(),
                c.getMember() != null ? c.getMember().getId() : null,
                c.getMember() != null ? c.getMember().getName() : null
        );
    }
}
