package com.pms.political_management_system.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String complaintTitle;

    @Column(nullable = false, length = 1000)
    private String complaintDescription;

    @Column(nullable = false)
    private String complaintStatus;

    @Column(nullable = false)
    private LocalDate complaintDate;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Complaint() {
    }

    public Complaint(Long id,
                     String complaintTitle,
                     String complaintDescription,
                     String complaintStatus,
                     LocalDate complaintDate,
                     Member member) {
        this.id = id;
        this.complaintTitle = complaintTitle;
        this.complaintDescription = complaintDescription;
        this.complaintStatus = complaintStatus;
        this.complaintDate = complaintDate;
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        this.complaintTitle = complaintTitle;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public String getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(String complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public LocalDate getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(LocalDate complaintDate) {
        this.complaintDate = complaintDate;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}