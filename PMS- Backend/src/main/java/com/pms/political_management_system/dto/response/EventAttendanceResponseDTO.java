package com.pms.political_management_system.dto.response;

public class EventAttendanceResponseDTO {

    private Long id;

    private Long eventId;
    private String eventName;

    private Long memberId;
    private String memberName;

    private String attendanceStatus;
    private String remarks;

    public EventAttendanceResponseDTO() {
    }

    public EventAttendanceResponseDTO(Long id,
                                      Long eventId,
                                      String eventName,
                                      Long memberId,
                                      String memberName,
                                      String attendanceStatus,
                                      String remarks) {
        this.id = id;
        this.eventId = eventId;
        this.eventName = eventName;
        this.memberId = memberId;
        this.memberName = memberName;
        this.attendanceStatus = attendanceStatus;
        this.remarks = remarks;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public String getAttendanceStatus() {
        return attendanceStatus;
    }

    public void setAttendanceStatus(String attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}