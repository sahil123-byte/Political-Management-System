package com.pms.political_management_system.dto.response;

import java.time.LocalDate;

public class FeedbackResponseDTO {

    private Long id;

    private String feedbackTitle;

    private String feedbackMessage;

    private Integer rating;

    private LocalDate feedbackDate;

    private Long memberId;

    private String memberName;

    public FeedbackResponseDTO() {
    }

    public FeedbackResponseDTO(Long id,
                               String feedbackTitle,
                               String feedbackMessage,
                               Integer rating,
                               LocalDate feedbackDate,
                               Long memberId,
                               String memberName) {
        this.id = id;
        this.feedbackTitle = feedbackTitle;
        this.feedbackMessage = feedbackMessage;
        this.rating = rating;
        this.feedbackDate = feedbackDate;
        this.memberId = memberId;
        this.memberName = memberName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFeedbackTitle() {
        return feedbackTitle;
    }

    public void setFeedbackTitle(String feedbackTitle) {
        this.feedbackTitle = feedbackTitle;
    }

    public String getFeedbackMessage() {
        return feedbackMessage;
    }

    public void setFeedbackMessage(String feedbackMessage) {
        this.feedbackMessage = feedbackMessage;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public LocalDate getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(LocalDate feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}