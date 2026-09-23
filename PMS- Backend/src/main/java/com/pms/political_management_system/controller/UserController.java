package com.pms.political_management_system.controller;

import com.pms.political_management_system.dto.request.UserRequestDTO;
import com.pms.political_management_system.dto.response.UserResponseDTO;
import com.pms.political_management_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ==========================
    // Get All Users
    // ==========================
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // ==========================
    // Get User By Id
    // ==========================
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER')")
    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // ==========================
    // Create User
    // ==========================
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserResponseDTO saveUser(@Valid @RequestBody UserRequestDTO requestDTO) {
        return userService.saveUser(requestDTO);
    }

    // ==========================
    // Update User
    // ==========================
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public UserResponseDTO updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequestDTO requestDTO) {

        return userService.updateUser(id, requestDTO);
    }

    // ==========================
    // Delete User
    // ==========================
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);
        return "User deleted successfully.";
    }

    // ==========================
    // Change Status
    // ==========================
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    public UserResponseDTO changeStatus(@PathVariable Long id) {

        return userService.changeStatus(id);
    }
}