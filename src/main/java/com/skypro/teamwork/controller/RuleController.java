package com.skypro.teamwork.controller;

import com.skypro.teamwork.model.Recommendation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/rule")
public class RuleController {

    private final RuleService service;

    public RuleController(RuleService service) {
        this.service = service;
    }

    @GetMapping("/{recommendationId}")
    public ResponseEntity<Map<String, Object>> getRulesOfRecommendation(@PathVariable UUID recommendationId) {
        Recommendation recommendation = service.findById(recommendationId);
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("id", UUID.randomUUID());
        data.put("product_name", recommendation.getName());
        data.put("product_id", recommendation.getId());
        data.put("product_text", recommendation.getText());
        data.put("rule", recommendation.getRules());
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createRulesOfRecommendation(
            @RequestParam(name = "product_name") String name,
            @RequestParam(name = "product_id") UUID id,
            @RequestParam(name = "product_text") String text,
            @RequestParam(name = "rule") List<Rule> rules
    ) {
        Recommendation recommendation = service.createRecommendation(name, id, text, rules);
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("id", UUID.randomUUID());
        data.put("product_name", recommendation.getName());
        data.put("product_id", recommendation.getId());
        data.put("product_text", recommendation.getText());
        data.put("rule", recommendation.getRules());
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity<Recommendation> deleteRecommendation(@PathVariable UUID recommendationId) {
        service.deleteRecommendation(recommendationId);
        return ResponseEntity.noContent().build();
    }

}
