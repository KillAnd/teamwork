package com.skypro.teamwork.controller;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.dto.RecommendationForUserDTO;
import com.skypro.teamwork.model.dto.mapper.RecommendationMapper;
import com.skypro.teamwork.service.RecommendationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RecommendationsControllerTest {

    @Mock
    private RecommendationsService recommendationService;

    @InjectMocks
    private RecommendationsController recommendationsController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetRecommendations() {
        UUID userId = UUID.randomUUID();
        RecommendationForUserDTO recommendation1 = new RecommendationForUserDTO();
        RecommendationForUserDTO recommendation2 = new RecommendationForUserDTO();
        List<RecommendationForUserDTO> recommendations = List.of(recommendation1, recommendation2);

        when(recommendationService.recommend(userId)).thenReturn(recommendations);

        ResponseEntity<Map<String, Object>> response = recommendationsController.getRecommendations(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userId, Objects.requireNonNull(response.getBody()).get("user_id"));
        assertEquals(recommendations, response.getBody().get("recommendations"));
    }

    @Test
    void testGetRecommendations_NoRecommendations() {
        UUID userId = UUID.randomUUID();
        List<RecommendationForUserDTO> recommendations = List.of();

        when(recommendationService.recommend(userId)).thenReturn(recommendations);

        ResponseEntity<Map<String, Object>> response = recommendationsController.getRecommendations(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userId, Objects.requireNonNull(response.getBody()).get("user_id"));
        assertEquals(recommendations, response.getBody().get("recommendations"));
    }

}