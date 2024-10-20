package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
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
//        if (simpleCreditRuleSet.checkRuleMatching(userID)) {
//            result.add(objectRepository.findById(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f")));
//        }
//        if (invest500RuleSet.checkRuleMatching(userID)) {
//            result.add(objectRepository.findById(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a")));
//        }
//        if (topSavingRuleSet.checkRuleMatching(userID)) {
//            result.add(objectRepository.findById(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925")));
//        }
        for (RecommendationRuleSet ruleSet : ruleSets) {
            if (ruleSet.checkRuleMatching(userID)) {
                result.add(ruleSet.getRecommendation());
            }
        }
        return result; // собранный ArrayList потом в контроллере перевести в json
    }
}
