package com.skypro.teamwork.service;

import com.skypro.teamwork.model.Recommendation;

import java.util.List;
import java.util.UUID;

public interface RecommendationsService {

    List<Recommendation> recommendationService(UUID userID);

}
