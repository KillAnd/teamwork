package com.skypro.teamwork.model.DTO.mapper;

import com.skypro.teamwork.model.DTO.RecommendationDTO;
import com.skypro.teamwork.model.Recommendation;

public class RecommendationMapper {

    public static RecommendationDTO mapToDTO(Recommendation recommendation) {
        RecommendationDTO recommendationDTO = new RecommendationDTO();
        recommendationDTO.setId(recommendation.getId());
        recommendationDTO.setName(recommendation.getName());
        recommendationDTO.setText(recommendation.getText());
        recommendationDTO.setRules(recommendation.getRules());
        recommendationDTO.setProductId(recommendation.getProductId());
        return recommendationDTO;
    }

    public static Recommendation mapFromDTO(RecommendationDTO recommendationDTO) {
        Recommendation recommendation = new Recommendation();
        recommendation.setId(recommendationDTO.getId());
        recommendation.setName(recommendationDTO.getName());
        recommendation.setText(recommendationDTO.getText());
        recommendation.setRules(recommendationDTO.getRules());
        recommendation.setProductId(recommendationDTO.getProductId());
        return recommendation;
    }

}
