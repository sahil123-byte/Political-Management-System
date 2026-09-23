package com.pms.political_management_system.repository;

import com.pms.political_management_system.entity.UserNotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserNotificationPreferenceRepository extends JpaRepository<UserNotificationPreference, Long> {

    List<UserNotificationPreference> findByUserId(Long userId);

    Optional<UserNotificationPreference> findByUserIdAndChannelType(Long userId, String channelType);
}
