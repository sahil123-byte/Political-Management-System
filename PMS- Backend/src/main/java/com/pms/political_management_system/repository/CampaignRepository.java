package com.pms.political_management_system.repository;

import com.pms.political_management_system.entity.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

}