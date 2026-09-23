package com.pms.political_management_system.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

// A reusable message template - e.g. "event_reminder" - with {{placeholder}}
// variables filled in at send time. Matches the Templates Table in the
// notification architecture: name, type, channel, content, variables.
@Entity
@Table(name = "notification_templates")
public class NotificationTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // TRANSACTIONAL or PROMOTIONAL
    @Column(nullable = false)
    private String type;

    // EMAIL, SMS, or INAPP
    @Column(nullable = false)
    private String channel;

    // The template body, e.g. "Hi {{name}}, your event {{eventName}} starts at {{time}}."
    @Column(nullable = false, length = 2000)
    private String content;

    // Comma-separated placeholder names this template expects, e.g. "name,eventName,time".
    @Column
    private String variables;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    public NotificationTemplate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
