package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.repository.RecommendationsRepository;
import com.skypro.teamwork.rulesets.RecommendationRuleSet;
import com.skypro.teamwork.service.RecommendationsService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {

    private final DynamicRecommendationRepository dynamicRecommendationRepository;
    private final RecommendationRuleSet ruleSet;

    public RecommendationsServiceImpl(DynamicRecommendationRepository dynamicRecommendationRepository, RecommendationRuleSet ruleSet) {
        this.dynamicRecommendationRepository = dynamicRecommendationRepository;
        this.ruleSet = ruleSet;
    }

    public List<Recommendation> recommend(UUID userID) {
        List<Recommendation> recommendations = dynamicRecommendationRepository.findAll();
        List<Recommendation> result = new ArrayList<>();
        for (Recommendation recommendation : recommendations) {
            if (ruleSet.checkRuleMatching(userID).isPresent()) {
                result.add(recommendation);
            }
        }
        return result;
    }
}