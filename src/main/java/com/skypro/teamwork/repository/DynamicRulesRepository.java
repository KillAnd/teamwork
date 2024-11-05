package com.skypro.teamwork.repository;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DynamicRulesRepository extends JpaRepository<Rule, Long> {
}
