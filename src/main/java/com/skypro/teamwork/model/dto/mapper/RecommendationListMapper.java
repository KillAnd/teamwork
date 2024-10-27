package com.skypro.teamwork.model.dto.mapper;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.dto.RecommendationDTO;
import com.skypro.teamwork.model.dto.RecommendationListDTO;

import java.util.LinkedList;
import java.util.List;

public class RecommendationListMapper {

    public static RecommendationListDTO mapToDTO(List<Recommendation> recommendations) {
        RecommendationListDTO recommendationDTOs = new RecommendationListDTO();
        List<RecommendationDTO> list = new LinkedList<>();
        for (Recommendation recommendation : recommendations) {
            list.add(RecommendationMapper.mapToDTO(recommendation));
        }
        recommendationDTOs.setData(list);
        return recommendationDTOs;
    }

    public static List<Recommendation> mapFromDTO(RecommendationListDTO listDTO) {
        List<Recommendation> list = new LinkedList<>();
        List<RecommendationDTO> recommendationDTOs = listDTO.getData();
        for (RecommendationDTO recommendationDTO : recommendationDTOs) {
            list.add(RecommendationMapper.mapFromDTO(recommendationDTO));
        }
        return list;
    }
}
