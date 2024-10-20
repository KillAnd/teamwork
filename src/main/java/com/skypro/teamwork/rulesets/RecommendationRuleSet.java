package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    //интерфейс получает id пользователя и возвращает объект рекомендации или null
    public Optional<Recommendation> checkRuleMatching(UUID userID);
}
