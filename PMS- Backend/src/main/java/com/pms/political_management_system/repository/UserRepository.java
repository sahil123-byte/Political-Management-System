package com.pms.political_management_system.repository;

import com.pms.political_management_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);

    // Used by the notification pipeline to resolve a Member (who a
    // Notification is addressed to) back to the User account whose
    // channel preferences (email/sms/in-app) should be checked.
    Optional<User> findByMemberId(Long memberId);
}