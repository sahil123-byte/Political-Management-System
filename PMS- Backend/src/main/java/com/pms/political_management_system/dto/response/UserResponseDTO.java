package com.pms.political_management_system.dto.response;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Boolean status;
    private String roleName;
    private String imageUrl;
    private Long memberId;
    private String memberName;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String name, String email, Boolean status,
                           String roleName, String imageUrl,
                           Long memberId, String memberName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
        this.roleName = roleName;
        this.imageUrl = imageUrl;
        this.memberId = memberId;
        this.memberName = memberName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
