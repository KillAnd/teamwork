package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.rulesets.DynamicRecommendation;
import com.skypro.teamwork.rulesets.RecommendationRuleSet;
import com.skypro.teamwork.service.RecommendationsService;
import com.skypro.teamwork.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {
    private final DynamicRecommendation dynamicRecommendation;
    private final List<RecommendationRuleSet> ruleSets;

    private DynamicRecommendationRepository dynamicRepository;

    public RecommendationsServiceImpl(DynamicRecommendation dynamicRecommendation, RuleService ruleService, List<RecommendationRuleSet> ruleSets) {
        this.dynamicRecommendation = dynamicRecommendation;
        this.ruleSets = ruleSets;
    }

    public List<Recommendation> recommendationService(UUID userID) {
        List<Recommendation> result = new ArrayList<>();
        // сначала проверяем старые правила
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