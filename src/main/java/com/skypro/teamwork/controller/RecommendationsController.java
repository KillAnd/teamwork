package com.skypro.teamwork.controller;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.dto.RecommendationForUserDTO;
import com.skypro.teamwork.service.RecommendationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            List<RecommendationForUserDTO> recommendations = recommendationService.recommend(userId);
            Map<String, Object> response = new HashMap<>();
            response.put("user_id", userId);
            response.put("recommendations", recommendations);
            return ResponseEntity.ok(response);
        }


}
