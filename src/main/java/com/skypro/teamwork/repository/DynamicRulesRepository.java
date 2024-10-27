package com.skypro.teamwork.repository;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DynamicRulesRepository extends JpaRepository<Rule, Long> {
}
