package com.skypro.teamwork.service;

import com.skypro.teamwork.model.dto.RecommendationDTO;
import com.skypro.teamwork.model.dto.RecommendationListDTO;
import com.skypro.teamwork.model.dto.mapper.RecommendationListMapper;
import com.skypro.teamwork.model.dto.mapper.RecommendationMapper;
import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import com.skypro.teamwork.repository.ArgumentsRepository;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.repository.DynamicRulesRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RuleService {

    private final DynamicRecommendationRepository recommendationRepository;

    private final DynamicRulesRepository ruleRepository;

    private final ArgumentsRepository argumentsRepository;

    public RuleService(DynamicRecommendationRepository recommendationRepository, DynamicRulesRepository ruleRepository, ArgumentsRepository argumentsRepository) {
        this.recommendationRepository = recommendationRepository;
        this.ruleRepository = ruleRepository;
        this.argumentsRepository = argumentsRepository;
    }

    public RecommendationListDTO getAll() {
        List<Recommendation> recommendations = recommendationRepository.findAll();
        return RecommendationListMapper.mapToDTO(recommendations);
    }

    public Optional<RecommendationDTO> createRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = RecommendationMapper.mapFromDTO(recommendationDTO);
        Optional<RecommendationDTO> result;
        List<Rule> rules = recommendation.getRules();
        boolean allIsOk = true;
        for (Rule rule : rules) {
            allIsOk = allIsOk && checkQuery(rule);
        }
        if (allIsOk) {
            recommendation = recommendationRepository.save(recommendation);
            for (Rule rule : rules) {
                rule.setRecommendation(recommendation);
                ruleRepository.save(rule);
            }
            recommendation.setRules(rules);
            result = Optional.of(RecommendationMapper.mapToDTO(recommendation));
        } else {
            result = Optional.empty();
        }
        return result;
    }

    public void deleteRecommendation(UUID recommendationId) {
        recommendationRepository.deleteById(recommendationId);
    }

    private boolean checkQuery(Rule rule) {
        String query = rule.getQuery();
        if (!List.of(ArgumentsRepository.QUERIES).contains(query)) {
            return false;
        } else if (!List.of(ArgumentsRepository.PRODUCT_TYPES).contains(rule.getArguments().get(0))) {
            return false;
        } else if (query.equals("TRANSACTION_SUM_COMPARE")) {
            if (!List.of(ArgumentsRepository.TRANSACTION_TYPES).contains(rule.getArguments().get(1))) {
                return false;
            } else if (!List.of(ArgumentsRepository.RELATION_OPERATORS).contains(rule.getArguments().get(2))) {
                return false;
            } else if (Integer.parseInt(rule.getArguments().get(3)) < 0) {
                return false;
            }
        } else if (query.equals("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW")) {
            if (!List.of(ArgumentsRepository.RELATION_OPERATORS).contains(rule.getArguments().get(1))) {
                return false;
            }
        }
        return true;
    }


}
