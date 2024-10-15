package com.skypro.teamwork.rulesets;

import java.util.UUID;

public interface RecommendationRuleSet {
    //интерфейс получает id пользователя и возвращает объект рекомендации или null
    public Optional<Recommendation> checkRuleMatching(UUID uesrID);
}
