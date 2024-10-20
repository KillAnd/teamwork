package com.skypro.teamwork.controller;

import com.skypro.teamwork.model.Recommendation;
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
        Recommendation recommendation1 = new Recommendation();
        Recommendation recommendation2 = new Recommendation();
        List<Recommendation> recommendations = List.of(recommendation1, recommendation2);

        when(recommendationService.recommendationService(userId)).thenReturn(recommendations);

        ResponseEntity<Map<String, Object>> response = recommendationsController.getRecommendations(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userId, Objects.requireNonNull(response.getBody()).get("user_id"));
        assertEquals(recommendations, response.getBody().get("recommendations"));
    }

    @Test
    void testGetRecommendations_NoRecommendations() {
        UUID userId = UUID.randomUUID();
        List<Recommendation> recommendations = List.of();

        when(recommendationService.recommendationService(userId)).thenReturn(recommendations);

        ResponseEntity<Map<String, Object>> response = recommendationsController.getRecommendations(userId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(userId, Objects.requireNonNull(response.getBody()).get("user_id"));
        assertEquals(recommendations, response.getBody().get("recommendations"));
    }

}