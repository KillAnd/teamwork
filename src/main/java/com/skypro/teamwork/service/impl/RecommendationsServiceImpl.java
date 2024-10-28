package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.rulesets.RecommendationRuleSet;
import com.skypro.teamwork.service.RecommendationsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {

    private final DynamicRecommendationRepository dynamicRecommendationRepository;
    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationsServiceImpl(DynamicRecommendationRepository dynamicRecommendationRepository, List<RecommendationRuleSet> ruleSets) {
        this.dynamicRecommendationRepository = dynamicRecommendationRepository;
        this.ruleSets = ruleSets;
    }

    public List<Recommendation> recommendationService(UUID userID) {
        List<Recommendation> result = new ArrayList<>();
        for (RecommendationRuleSet ruleSet : ruleSets) {
            if (ruleSet.checkRuleMatching(userID).isPresent()) {
                result.add(ruleSet.getRecommendation());
            }
        }
        return result;
    }
}