package com.skypro.teamwork.repository;

import com.skypro.teamwork.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DynamicRecommendationRepository extends JpaRepository<Recommendation, Long> {
}
