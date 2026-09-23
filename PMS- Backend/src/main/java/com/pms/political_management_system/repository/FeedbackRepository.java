package com.pms.political_management_system.repository;

import com.pms.political_management_system.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("SELECT COALESCE(AVG(f.rating), 0) FROM Feedback f")
    Double getAverageRating();

}