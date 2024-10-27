package com.skypro.teamwork.controller;

import com.skypro.teamwork.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.skypro.teamwork.model.DTO.RecommendationDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RuleController {

    private RecommendationDTO recommendationDTO;
    private final RuleService service;

    public RuleController(RuleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<RecommendationDTO>>> getRules() {
        List<RecommendationDTO> data = service.getAll();
        Map<String, List<RecommendationDTO>> response = new HashMap<>();
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RecommendationDTO> createRulesOfRecommendation(@RequestBody RecommendationDTO recommendationDTO) {
        this.recommendationDTO = recommendationDTO;
        ResponseEntity<RecommendationDTO> responseEntity;
        if (service.createRecommendation(recommendationDTO).isPresent()) {
            responseEntity = ResponseEntity.ok(service.createRecommendation(recommendationDTO).get());
        } else {
            responseEntity = ResponseEntity.badRequest().build();
        }
        return responseEntity;
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity deleteRecommendation(@PathVariable UUID recommendationId) {
        service.deleteRecommendation(recommendationId);
        return ResponseEntity.noContent().build();
    }


}
