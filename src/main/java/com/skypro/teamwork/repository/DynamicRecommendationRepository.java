package com.skypro.teamwork.repository;

import com.skypro.teamwork.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface DynamicRecommendationRepository extends JpaRepository<Recommendation, UUID> {

    void deleteById(UUID recommendationId);
}
