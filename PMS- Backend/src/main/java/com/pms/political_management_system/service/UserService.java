package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.UserRequestDTO;
import com.pms.political_management_system.dto.response.UserResponseDTO;
import com.pms.political_management_system.entity.Member;
import com.pms.political_management_system.entity.Role;
import com.pms.political_management_system.entity.User;
import com.pms.political_management_system.repository.MemberRepository;
import com.pms.political_management_system.repository.RoleRepository;
import com.pms.political_management_system.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       MemberRepository memberRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===========================
    // Get All Users
    // ===========================

    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    // ===========================
    // Get User By Id
    // ===========================

    public UserResponseDTO getUserById(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return mapToResponseDTO(user);
    }

    // ===========================
    // Create User
    // ===========================

    public UserResponseDTO saveUser(UserRequestDTO requestDTO) {

        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (requestDTO.getPassword() == null || requestDTO.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }

        if (requestDTO.getPassword().length() < 6 || requestDTO.getPassword().length() > 20) {
            throw new RuntimeException("Password must be between 6 and 20 characters");
        }

        if (requestDTO.getRoleId() == null) {
            throw new RuntimeException("Role is required");
        }

        Role role = roleRepository.findById(requestDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        User user = new User();

        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        user.setStatus(true);
        user.setRole(role);
        user.setImageUrl(requestDTO.getImageUrl());
        user.setMember(resolveMember(requestDTO.getMemberId()));

        User savedUser = userRepository.save(user);

        return mapToResponseDTO(savedUser);
    }

    // ===========================
    // Update User
    // ===========================

    public UserResponseDTO updateUser(Long id, UserRequestDTO requestDTO) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getEmail().equals(requestDTO.getEmail())
                && userRepository.existsByEmail(requestDTO.getEmail())) {

            throw new RuntimeException("Email already exists");
        }

        if (requestDTO.getRoleId() == null) {
            throw new RuntimeException("Role is required");
        }

        Role role = roleRepository.findById(requestDTO.getRoleId())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setRole(role);
        user.setImageUrl(requestDTO.getImageUrl());
        user.setMember(resolveMember(requestDTO.getMemberId()));

        if (requestDTO.getPassword() != null &&
                !requestDTO.getPassword().isBlank()) {

            if (requestDTO.getPassword().length() < 6 || requestDTO.getPassword().length() > 20) {
                throw new RuntimeException("Password must be between 6 and 20 characters");
            }

            user.setPassword(
                    passwordEncoder.encode(requestDTO.getPassword())
            );
        }

        User updatedUser = userRepository.save(user);

        return mapToResponseDTO(updatedUser);
    }

    // ===========================
    // Delete User
    // ===========================

    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
    }

    // ===========================
    // Change Status
    // ===========================

    public UserResponseDTO changeStatus(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setStatus(!user.getStatus());

        User updatedUser = userRepository.save(user);

        return mapToResponseDTO(updatedUser);
    }

    // ===========================
    // Helpers
    // ===========================

    private Member resolveMember(Long memberId) {

        if (memberId == null) {
            return null;
        }

        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
    }

    private UserResponseDTO mapToResponseDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getStatus(),
                user.getRole() != null
                        ? user.getRole().getRoleName()
                        : null,
                user.getImageUrl(),
                user.getMember() != null ? user.getMember().getId() : null,
                user.getMember() != null ? user.getMember().getName() : null
        );
    }

}
