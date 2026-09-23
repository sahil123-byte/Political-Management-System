package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.response.AnalyticsResponseDTO;
import com.pms.political_management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    public AnalyticsResponseDTO getAnalytics() {

        AnalyticsResponseDTO analytics = new AnalyticsResponseDTO();

        analytics.setTotalUsers(userRepository.count());
        analytics.setTotalMembers(memberRepository.count());
        analytics.setTotalVoters(voterRepository.count());

        analytics.setTotalCampaigns(campaignRepository.count());
        analytics.setTotalEvents(eventRepository.count());
        analytics.setTotalOrganizations(organizationRepository.count());

        analytics.setTotalComplaints(complaintRepository.count());

        analytics.setPendingComplaints(
                complaintRepository.countByComplaintStatus("Pending")
        );

        analytics.setResolvedComplaints(
                complaintRepository.countByComplaintStatus("Resolved")
        );

        analytics.setTotalFeedbacks(feedbackRepository.count());

        Double averageRating = feedbackRepository.getAverageRating();

        analytics.setAverageFeedbackRating(
                averageRating != null ? averageRating : 0.0
        );

        analytics.setTotalNotifications(notificationRepository.count());

        return analytics;
    }
}