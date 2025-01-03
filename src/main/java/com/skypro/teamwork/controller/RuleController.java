package com.skypro.teamwork.controller;

import com.skypro.teamwork.model.dto.RecommendationListDTO;
import com.skypro.teamwork.model.dto.RecommendationStatsDTO;
import com.skypro.teamwork.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.skypro.teamwork.model.dto.RecommendationDTO;

import java.util.*;

@RestController
@RequestMapping("/rule")
public class RuleController {

    private final RuleService service;

    public RuleController(RuleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<RecommendationListDTO> getRules() {
        RecommendationListDTO data = service.getAll();
        return ResponseEntity.ok(data);
    }

    @PostMapping
    public ResponseEntity<RecommendationDTO> createRulesOfRecommendation(
            @RequestBody RecommendationDTO recommendationDTO) {
        RecommendationDTO response = service.createRecommendation(recommendationDTO).orElse(null);
        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity<Object> deleteRecommendation(@PathVariable UUID recommendationId) {
        if (service.deleteRecommendation(recommendationId)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/stats")
    public Set<RecommendationStatsDTO> getStats() {
        return service.getStats();
    }


}
