package com.pms.political_management_system.service;

import com.pms.political_management_system.entity.User;
import com.pms.political_management_system.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private final UserRepository userRepository;

    public CurrentUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Returns the logged-in User entity, or null if there's no authenticated
    // user (e.g. a public endpoint being called without a token).
    public User getCurrentUser() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        String email = authentication.getName();

        if (email == null || email.equals("anonymousUser")) {
            return null;
        }

        return userRepository.findByEmail(email).orElse(null);
    }

    // The Member record linked to the current user's account (if any).
    // Used to scope "Self"/"Own" access for the MEMBER role.
    public Long getCurrentMemberId() {

        User user = getCurrentUser();

        if (user == null || user.getMember() == null) {
            return null;
        }

        return user.getMember().getId();
    }

    public boolean isMember() {

        User user = getCurrentUser();

        return user != null
                && user.getRole() != null
                && "MEMBER".equalsIgnoreCase(user.getRole().getRoleName());
    }
}
