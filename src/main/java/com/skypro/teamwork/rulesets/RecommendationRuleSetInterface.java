package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSetInterface {
    Optional<Recommendation> checkRuleMatching(UUID userID, Recommendation recommendation);
}
