package com.skypro.teamwork.repository;

import com.skypro.teamwork.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DynamicRecommendationRepository extends JpaRepository<Recommendation, UUID> {
    void deleteById(UUID recommendationId);
}
