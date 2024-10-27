package com.skypro.teamwork.service;

import com.skypro.teamwork.model.DTO.RecommendationDTO;
import com.skypro.teamwork.model.DTO.mapper.RecommendationMapper;
import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.repository.DynamicRulesRepository;
import com.skypro.teamwork.repository.RecommendationsRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RuleService {

    private final DynamicRecommendationRepository recommendationRepository;

    private final DynamicRulesRepository ruleRepository;

    public RuleService(DynamicRecommendationRepository recommendationRepository, DynamicRulesRepository ruleRepository) {
        this.recommendationRepository = recommendationRepository;
        this.ruleRepository = ruleRepository;
    }

    public List<RecommendationDTO> getAll() {
        List<Recommendation> recommendations = recommendationRepository.findAll();
        List<RecommendationDTO> recommendationDTOs = new LinkedList<>();
        for (Recommendation recommendation : recommendations) {
            recommendationDTOs.add(RecommendationMapper.mapToDTO(recommendation));
        }
        return recommendationDTOs;
    }

    public Optional<RecommendationDTO> createRecommendation(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = RecommendationMapper.mapFromDTO(recommendationDTO);
        List<Rule> rules = recommendation.getRules();
        recommendation= recommendationRepository.save(recommendation);
        for (Rule rule : rules){
            rule.setRecommendation(recommendation);
            ruleRepository.save(rule);
        }
        recommendation.setRules(rules);
        return Optional.of(RecommendationMapper.mapToDTO(recommendation));
    }

    public void deleteRecommendation(UUID recommendationId) {
        recommendationRepository.deleteById(recommendationId);
    }


}
