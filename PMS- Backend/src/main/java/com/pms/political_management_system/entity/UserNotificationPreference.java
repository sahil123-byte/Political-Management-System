package com.pms.political_management_system.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// Whether a given user wants to receive notifications on a given channel.
// Checked by the Kafka consumers (see notification.channel package) before
// dispatching - an opted-out channel is skipped entirely.
@Entity
@Table(
        name = "user_notification_preferences",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "channel_type"})
)
public class UserNotificationPreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // EMAIL, SMS, or INAPP
    @Column(name = "channel_type", nullable = false)
    private String channelType;

    @Column(nullable = false)
    private Boolean isEnabled = true;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public UserNotificationPreference() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
