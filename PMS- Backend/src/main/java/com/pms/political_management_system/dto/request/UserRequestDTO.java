package com.pms.political_management_system.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRequestDTO {

    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email is required")
    private String email;

    // Not @NotBlank here on purpose: on update, the password can be left blank
    // to keep the existing one. UserService validates it's required on create,
    // and validates length (6-20) only when a new value is actually provided.
    private String password;

    private Long roleId;

    private String imageUrl;

    // Optional link to a Member record (grassroots worker profile).
    // Used for the MEMBER role's "Self"/"Own" scoped access.
    private Long memberId;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String name, String email, String password, Long roleId, String imageUrl, Long memberId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
        this.imageUrl = imageUrl;
        this.memberId = memberId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
}
