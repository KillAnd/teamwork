package com.skypro.teamwork.model.dto.mapper;

import com.skypro.teamwork.model.dto.RecommendationDTO;
import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.dto.RecommendationForUserDTO;

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

    public static RecommendationForUserDTO mapToUserDTO(Recommendation recommendation) {
        RecommendationForUserDTO recommendationForUserDTO = new RecommendationForUserDTO();
        recommendationForUserDTO.setProductId(recommendation.getProductId());
        recommendationForUserDTO.setName(recommendation.getName());
        recommendationForUserDTO.setText(recommendation.getText());
        return recommendationForUserDTO;
    }

}
