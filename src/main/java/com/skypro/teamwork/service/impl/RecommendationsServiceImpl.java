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
    private final ObjectRepository objectRepository;
    private final List<RecommendationRuleSet> ruleSets;

    public RecommendationsServiceImpl(ObjectRepository objectRepository, List<RecommendationRuleSet> ruleSets) {
        this.objectRepository = objectRepository;
        this.ruleSets = ruleSets;
    }

    public List<Recommendation> recommendationService(UUID userID) {
        List<Recommendation> result = new ArrayList<>();
        // сначала проверяем старые правила
        for (RecommendationRuleSet ruleSet : ruleSets) {
            if (ruleSet.checkRuleMatching(userID).isPresent()) {
                result.add(ruleSet.getRecommendation());
            }
        }
        // временный лист для хранения всех динамических правил
        // и запрос в БД всех объектов типа Rule
        List<Rule> dynamicRules = objectRepository.getAllRules();
        // проверяем каждое динамическое правило
        for (Rule rule : dynamicRules) {
            if (dynamicRules.checkRuleMatching(userID).isPresent()) {
                result.add(rule.getRecommendation());
            }
        }
        return result;
    }
}
