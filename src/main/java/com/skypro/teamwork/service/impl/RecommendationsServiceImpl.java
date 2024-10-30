package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.rulesets.DynamicRecommendation;
import com.skypro.teamwork.rulesets.RecommendationRuleSet;
import com.skypro.teamwork.service.RecommendationsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {

    private final DynamicRecommendation dynamicRecommendation;
    private final List<RecommendationRuleSet> ruleSets;
    private final DynamicRecommendationRepository dynamicRepository;

    public RecommendationsServiceImpl(DynamicRecommendation dynamicRecommendation, List<RecommendationRuleSet> ruleSets, DynamicRecommendationRepository dynamicRepository) {
        this.dynamicRecommendation = dynamicRecommendation;
        this.ruleSets = ruleSets;
        this.dynamicRepository = dynamicRepository;
    }

    public List<Recommendation> recommendationService(UUID userID) {
        List<Recommendation> result = new ArrayList<>();
        for (RecommendationRuleSet ruleSet : ruleSets) {
            if (ruleSet.checkRuleMatching(userID).isPresent()) {
                result.add(ruleSet.checkRuleMatching(userID).get());
            }
        }
        // временный лист для хранения всех динамических правил
        List<Recommendation> dynamicRules = dynamicRepository.findAll();
        // проверяем каждое динамическое правило
        for (Recommendation dynamicRule : dynamicRules) {
            if (dynamicRecommendation.checkRuleMatching(dynamicRule, userID)) {
                result.add(dynamicRule);
            }
        }
        return result;
    }
}