package com.skypro.teamwork.repository;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DynamicRecommendationRepository extends JpaRepository<Recommendation, Long> {
    void deleteById(UUID recommendationId);
}
