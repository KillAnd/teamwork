package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    //интерфейс получает id пользователя и возвращает объект рекомендации или Optional.empty()
    Optional<Recommendation> checkRuleMatching(UUID userID);
    Recommendation getRecommendation();
}
