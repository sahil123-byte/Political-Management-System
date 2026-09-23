package com.pms.political_management_system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "feedbacks")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String feedbackTitle;

    @Column(nullable = false, length = 1000)
    private String feedbackMessage;

    @Column(nullable = false)
    private Integer rating;

    @Column(nullable = false)
    private LocalDate feedbackDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Feedback() {
    }

    public Feedback(Long id,
                    String feedbackTitle,
                    String feedbackMessage,
                    Integer rating,
                    LocalDate feedbackDate,
                    Member member) {
        this.id = id;
        this.feedbackTitle = feedbackTitle;
        this.feedbackMessage = feedbackMessage;
        this.rating = rating;
        this.feedbackDate = feedbackDate;
        this.member = member;
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}