package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.dto.RecommendationForUserDTO;
import com.skypro.teamwork.model.dto.mapper.RecommendationMapper;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.repository.RecommendationsRepository;
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

    public RecommendationsServiceImpl(DynamicRecommendationRepository dynamicRecommendationRepository, RecommendationRuleSet ruleSet, StatsRepository statsRepository) {
        this.dynamicRecommendationRepository = dynamicRecommendationRepository;
        this.ruleSet = ruleSet;
        this.statsRepository = statsRepository;
    }

    public Set<RecommendationForUserDTO> recommend(UUID userID) {
        HashSet<RecommendationForUserDTO> recommendationForUserDTOS = new HashSet<>();
        for (Recommendation recommendation : dynamicRecommendationRepository.findAll()) {
            if (ruleSet.checkRuleMatching(recommendation, userID)) {
                Optional<RecommendationStat> stat = statsRepository.findById(recommendation.getId());
                if (stat.isPresent()) {
                    RecommendationStat newStat = stat.get();
                    newStat.setCounter(newStat.getCounter() + 1);
                    statsRepository.save(newStat);
                }
                recommendationForUserDTOS.add(RecommendationMapper.mapToUserDTO(recommendation));
            }
        }
        return recommendationForUserDTOS;
    }
}