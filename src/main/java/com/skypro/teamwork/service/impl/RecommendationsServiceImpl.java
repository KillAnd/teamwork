package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.RecommendationStat;
import com.skypro.teamwork.model.dto.RecommendationForUserDTO;
import com.skypro.teamwork.model.dto.mapper.RecommendationMapper;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.repository.StatsRepository;
import com.skypro.teamwork.rulesets.DynamicRecommendation;
import com.skypro.teamwork.rulesets.DynamicRecommendationImpl;
import com.skypro.teamwork.rulesets.RecommendationRuleSet;
import com.skypro.teamwork.service.RecommendationsService;
import com.skypro.teamwork.service.RuleService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationsServiceImpl implements RecommendationsService {

    private final DynamicRecommendation dynamicRecommendation;

    private final List<RecommendationRuleSet> ruleSets;

    private final StatsRepository statsRepository;

    private final DynamicRecommendationRepository dynamicRepository;


    public RecommendationsServiceImpl(DynamicRecommendationImpl dynamicRecommendation, List<RecommendationRuleSet> ruleSets, StatsRepository statsRepository, DynamicRecommendationRepository dynamicRepository) {
        this.dynamicRecommendation = dynamicRecommendation;
        this.ruleSets = ruleSets;
        this.statsRepository = statsRepository;
        this.dynamicRepository = dynamicRepository;
    }

    public List<RecommendationForUserDTO> recommend(UUID userID) {
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
        return result.stream().map(RecommendationMapper::mapToUserDTO).collect(Collectors.toList());
    }
}