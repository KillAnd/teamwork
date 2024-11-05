package com.skypro.teamwork.repository;

import com.skypro.teamwork.model.RecommendationStat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StatsRepository extends JpaRepository<RecommendationStat, UUID> {
}
