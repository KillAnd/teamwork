package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.RecommendationStat;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.repository.StatsRepository;
import com.skypro.teamwork.rulesets.DynamicRecommendation;
import com.skypro.teamwork.rulesets.RecommendationRuleSet;
import com.skypro.teamwork.service.RecommendationsService;
import com.skypro.teamwork.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {

    private final DynamicRecommendationRepository dynamicRecommendationRepository;

    private final RecommendationRuleSet ruleSet;

    private final StatsRepository statsRepository;


    public RecommendationsServiceImpl(DynamicRecommendation dynamicRecommendation, List<RecommendationRuleSet> ruleSets,
                                      DynamicRecommendationRepository dynamicRepository, StatsRepository statsRepository) {
        this.dynamicRecommendation = dynamicRecommendation;
        this.ruleSets = ruleSets;
        this.dynamicRepository = dynamicRepository;
        this.statsRepository = statsRepository;
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
                Optional<RecommendationStat> stat = statsRepository.findById(dynamicRule.getId());
                //Если запись в бд есть:
                if (stat.isPresent()) {
                    //Берем значение статистики
                    RecommendationStat newStat = stat.get();
                    //Увеличиваем на единицу
                    newStat.setCounter(newStat.getCounter() + 1);
                    //Перезаписываем старое значение новым
                    statsRepository.save(newStat);
                } else {
                    //Создаем запись в бд если ее нет
                    statsRepository.save(new RecommendationStat(dynamicRule.getId(), 0));
                }
                result.add(dynamicRule);
            }
        }
        return recommendationForUserDTOS;
    }
}