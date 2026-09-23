package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.FeedbackRequestDTO;
import com.pms.political_management_system.dto.response.FeedbackResponseDTO;
import com.pms.political_management_system.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Get All Feedbacks
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public List<FeedbackResponseDTO> getAllFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    // Get Feedback By Id
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public FeedbackResponseDTO getFeedbackById(@PathVariable Long id) {
        return feedbackService.getFeedbackById(id);
    }

    // Create Feedback
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public FeedbackResponseDTO saveFeedback(
            @Valid @RequestBody FeedbackRequestDTO requestDTO) {

        return feedbackService.saveFeedback(requestDTO);
    }

    // Update Feedback
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @PutMapping("/{id}")
    public FeedbackResponseDTO updateFeedback(
            @PathVariable Long id,
            @Valid @RequestBody FeedbackRequestDTO requestDTO) {

        return feedbackService.updateFeedback(id, requestDTO);
    }

    // Delete Feedback
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @DeleteMapping("/{id}")
    public String deleteFeedback(@PathVariable Long id) {
        return feedbackService.deleteFeedback(id);
    }
}