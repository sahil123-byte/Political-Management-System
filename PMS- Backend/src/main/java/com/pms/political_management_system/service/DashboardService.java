package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.response.DashboardResponseDTO;
import com.pms.political_management_system.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ConstituencyRepository constituencyRepository;

    @Autowired
    private BoothRepository boothRepository;

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

    // Dashboard Data
    public DashboardResponseDTO getDashboardData() {

        DashboardResponseDTO dashboard = new DashboardResponseDTO();

        dashboard.setTotalUsers(userRepository.count());
        dashboard.setTotalParties(partyRepository.count());
        dashboard.setTotalStates(stateRepository.count());
        dashboard.setTotalDistricts(districtRepository.count());
        dashboard.setTotalConstituencies(constituencyRepository.count());
        dashboard.setTotalBooths(boothRepository.count());
        dashboard.setTotalMembers(memberRepository.count());
        dashboard.setTotalVoters(voterRepository.count());
        dashboard.setTotalCampaigns(campaignRepository.count());
        dashboard.setTotalEvents(eventRepository.count());
        dashboard.setTotalOrganizations(organizationRepository.count());

        dashboard.setTotalComplaints(complaintRepository.count());

        dashboard.setPendingComplaints(
                complaintRepository.countByComplaintStatus("Pending")
        );

        dashboard.setResolvedComplaints(
                complaintRepository.countByComplaintStatus("Resolved")
        );

        dashboard.setTotalFeedbacks(feedbackRepository.count());

        Double averageRating = feedbackRepository.getAverageRating();
        dashboard.setAverageFeedbackRating(
                averageRating != null ? averageRating : 0.0
        );

        dashboard.setTotalNotifications(notificationRepository.count());

        return dashboard;
    }
}