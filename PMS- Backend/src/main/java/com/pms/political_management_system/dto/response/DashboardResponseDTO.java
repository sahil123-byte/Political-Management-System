package com.pms.political_management_system.dto.response;

public class DashboardResponseDTO {

    private long totalUsers;
    private long totalParties;
    private long totalStates;
    private long totalDistricts;
    private long totalConstituencies;
    private long totalBooths;
    private long totalMembers;
    private long totalVoters;
    private long totalCampaigns;
    private long totalEvents;
    private long totalOrganizations;
    private long totalComplaints;
    private long pendingComplaints;
    private long resolvedComplaints;
    private long totalFeedbacks;
    private double averageFeedbackRating;
    private long totalNotifications;

    public DashboardResponseDTO() {
    }

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalParties() {
        return totalParties;
    }

    public void setTotalParties(long totalParties) {
        this.totalParties = totalParties;
    }

    public long getTotalStates() {
        return totalStates;
    }

    public void setTotalStates(long totalStates) {
        this.totalStates = totalStates;
    }

    public long getTotalDistricts() {
        return totalDistricts;
    }

    public void setTotalDistricts(long totalDistricts) {
        this.totalDistricts = totalDistricts;
    }

    public long getTotalConstituencies() {
        return totalConstituencies;
    }

    public void setTotalConstituencies(long totalConstituencies) {
        this.totalConstituencies = totalConstituencies;
    }

    public long getTotalBooths() {
        return totalBooths;
    }

    public void setTotalBooths(long totalBooths) {
        this.totalBooths = totalBooths;
    }

    public long getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(long totalMembers) {
        this.totalMembers = totalMembers;
    }

    public long getTotalVoters() {
        return totalVoters;
    }

    public void setTotalVoters(long totalVoters) {
        this.totalVoters = totalVoters;
    }

    public long getTotalCampaigns() {
        return totalCampaigns;
    }

    public void setTotalCampaigns(long totalCampaigns) {
        this.totalCampaigns = totalCampaigns;
    }

    public long getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(long totalEvents) {
        this.totalEvents = totalEvents;
    }

    public long getTotalOrganizations() {
        return totalOrganizations;
    }

    public void setTotalOrganizations(long totalOrganizations) {
        this.totalOrganizations = totalOrganizations;
    }

    public long getTotalComplaints() {
        return totalComplaints;
    }

    public void setTotalComplaints(long totalComplaints) {
        this.totalComplaints = totalComplaints;
    }

    public long getPendingComplaints() {
        return pendingComplaints;
    }

    public void setPendingComplaints(long pendingComplaints) {
        this.pendingComplaints = pendingComplaints;
    }

    public long getResolvedComplaints() {
        return resolvedComplaints;
    }

    public void setResolvedComplaints(long resolvedComplaints) {
        this.resolvedComplaints = resolvedComplaints;
    }

    public long getTotalFeedbacks() {
        return totalFeedbacks;
    }

    public void setTotalFeedbacks(long totalFeedbacks) {
        this.totalFeedbacks = totalFeedbacks;
    }

    public double getAverageFeedbackRating() {
        return averageFeedbackRating;
    }

    public void setAverageFeedbackRating(double averageFeedbackRating) {
        this.averageFeedbackRating = averageFeedbackRating;
    }

    public long getTotalNotifications() {
        return totalNotifications;
    }

    public void setTotalNotifications(long totalNotifications) {
        this.totalNotifications = totalNotifications;
    }
}