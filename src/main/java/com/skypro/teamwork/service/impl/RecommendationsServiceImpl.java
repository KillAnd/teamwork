package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.repository.DynamicRulesRepository;
import com.skypro.teamwork.repository.ObjectRepository;
import com.skypro.teamwork.rulesets.RecommendationRuleSet;
import com.skypro.teamwork.service.RecommendationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {
    @Autowired
    ObjectRepository objectRepository;
    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationsServiceImpl(List<RecommendationRuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }

    public List<Recommendation> recommendationService(UUID userID) {
        List<Recommendation> result = new ArrayList<>();
        for (RecommendationRuleSet ruleSet : ruleSets) {
            if (ruleSet.checkRuleMatching(userID).isPresent()) {
                result.add(ruleSet.getRecommendation());
            }
        }
        return result; // собранный ArrayList потом в контроллере перевести в json
    }

    @Autowired
    private DynamicRecommendationRepository dynamicRec;

    public void saveRecommendation(Recommendation recommendation) {
        dynamicRec.save(recommendation);
    }
}