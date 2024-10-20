package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;

import java.util.UUID;

public interface RecommendationRuleSet {
    //интерфейс получает id пользователя и возвращает объект рекомендации или null
    boolean checkRuleMatching(UUID userID);
    Recommendation getRecommendation();

}
