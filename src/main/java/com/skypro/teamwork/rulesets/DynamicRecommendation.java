package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;

import java.util.UUID;

public interface DynamicRecommendation {

    //интерфейс получает id пользователя и возвращает объект рекомендации или null
    boolean checkRuleMatching(Recommendation recommendation, UUID userID);
}
