package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.FeedbackRequestDTO;
import com.pms.political_management_system.dto.response.FeedbackResponseDTO;
import com.pms.political_management_system.entity.Feedback;
import com.pms.political_management_system.entity.Member;
import com.pms.political_management_system.repository.FeedbackRepository;
import com.pms.political_management_system.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CurrentUserService currentUserService;

    // Get All Feedbacks
    public List<FeedbackResponseDTO> getAllFeedbacks() {

        // MEMBER role: "Own" access - only see their own submitted feedback.
        if (currentUserService.isMember()) {

            Long memberId = currentUserService.getCurrentMemberId();

            if (memberId == null) {
                return List.of();
            }

            return feedbackRepository.findAll()
                    .stream()
                    .filter(f -> f.getMember() != null && memberId.equals(f.getMember().getId()))
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        }

        return feedbackRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Save Feedback
    public FeedbackResponseDTO saveFeedback(FeedbackRequestDTO requestDTO) {

        Long memberId = requestDTO.getMemberId();

        // MEMBER role: always attribute feedback to their own Member record.
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

        Feedback feedback = new Feedback();

        feedback.setFeedbackTitle(requestDTO.getFeedbackTitle());
        feedback.setFeedbackMessage(requestDTO.getFeedbackMessage());
        feedback.setRating(requestDTO.getRating());
        feedback.setFeedbackDate(requestDTO.getFeedbackDate());
        feedback.setMember(member);

        Feedback saved = feedbackRepository.save(feedback);

        return mapToResponse(saved);
    }

    // Get Feedback By Id
    public FeedbackResponseDTO getFeedbackById(Long id) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback Not Found"));

        assertOwnFeedbackIfMember(feedback);

        return mapToResponse(feedback);
    }

    // Update Feedback
    public FeedbackResponseDTO updateFeedback(Long id,
                                              FeedbackRequestDTO requestDTO) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback Not Found"));

        if (requestDTO.getMemberId() == null) {
            throw new RuntimeException("Member is required");
        }

        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member Not Found"));

        feedback.setFeedbackTitle(requestDTO.getFeedbackTitle());
        feedback.setFeedbackMessage(requestDTO.getFeedbackMessage());
        feedback.setRating(requestDTO.getRating());
        feedback.setFeedbackDate(requestDTO.getFeedbackDate());
        feedback.setMember(member);

        Feedback updated = feedbackRepository.save(feedback);

        return mapToResponse(updated);
    }

    // Delete Feedback
    public String deleteFeedback(Long id) {

        Feedback feedback = feedbackRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Feedback Not Found"));

        feedbackRepository.delete(feedback);

        return "Feedback Deleted Successfully";
    }

    private void assertOwnFeedbackIfMember(Feedback feedback) {

        if (!currentUserService.isMember()) {
            return;
        }

        Long memberId = currentUserService.getCurrentMemberId();

        if (feedback.getMember() == null || !feedback.getMember().getId().equals(memberId)) {
            throw new RuntimeException("You are not allowed to view this feedback");
        }
    }

    // Mapping Method (null-safe against a missing/deleted member link)
    private FeedbackResponseDTO mapToResponse(Feedback f) {

        return new FeedbackResponseDTO(
                f.getId(),
                f.getFeedbackTitle(),
                f.getFeedbackMessage(),
                f.getRating(),
                f.getFeedbackDate(),
                f.getMember() != null ? f.getMember().getId() : null,
                f.getMember() != null ? f.getMember().getName() : null
        );
    }
}
