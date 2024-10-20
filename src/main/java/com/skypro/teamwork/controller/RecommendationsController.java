package com.skypro.teamwork.controller;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.service.RecommendationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/recommendations")
public class RecommendationsController {
        private final RecommendationsService recommendationService;

        public RecommendationsController(RecommendationsService recommendationService) {
            this.recommendationService = recommendationService;
        }

        @GetMapping("/{userId}")
        public ResponseEntity<Map<String, Object>> getRecommendations(@PathVariable UUID userId) {
            List<Recommendation> recommendations = recommendationService.recommendationService(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("user_id", userId);
            response.put("recommendations", recommendations);
            return ResponseEntity.ok(response);
        }

}
