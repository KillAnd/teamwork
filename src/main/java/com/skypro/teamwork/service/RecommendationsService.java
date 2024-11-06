package com.skypro.teamwork.service;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import com.skypro.teamwork.model.dto.RecommendationForUserDTO;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RecommendationsService {

    List<RecommendationForUserDTO> recommend(UUID userID);

}
