package com.pms.political_management_system.service;

import com.pms.political_management_system.dto.request.LoginRequestDTO;
import com.pms.political_management_system.dto.response.LoginResponseDTO;
import com.pms.political_management_system.entity.User;
import com.pms.political_management_system.repository.UserRepository;
import com.pms.political_management_system.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        if (Boolean.FALSE.equals(user.getStatus())) {
            throw new RuntimeException("Your account has been disabled. Please contact the administrator.");
        }

        if (user.getRole() == null) {
            throw new RuntimeException("Role is not assigned to this user.");
        }

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().getRoleName()
        );

        return new LoginResponseDTO(
                token,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getRoleName(),
                user.getImageUrl()
        );
    }
}