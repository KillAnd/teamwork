package com.skypro.teamwork.controller;

import com.skypro.teamwork.model.Recommendation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/rule")
public class RuleController {

    private final RuleService service;

    public RuleController(RuleService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<Map<String, Object>>>> getRules() {
        List<Recommendation> recommendations = service.findAll();
        Map<String, List<Map<String, Object>>> response = new HashMap<>();
        List<Map<String, Object>> data = new ArrayList<>();
        for (Recommendation recommendation : recommendations) {
            Map<String, Object> ruleSet = new HashMap<>();
            ruleSet.put("id", UUID.randomUUID());
            ruleSet.put("product_name", recommendation.getName());
            ruleSet.put("product_id", recommendation.getId());
            ruleSet.put("product_text", recommendation.getText());
            ruleSet.put("rule", recommendation.getRules());
            data.add(ruleSet);
        }
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
        response.put("id", UUID.randomUUID());
        response.put("product_name", recommendation.getName());
        response.put("product_id", recommendation.getId());
        response.put("product_text", recommendation.getText());
        response.put("rule", recommendation.getRules());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{recommendationId}")
    public ResponseEntity deleteRecommendation(@PathVariable UUID recommendationId) {
        service.deleteRecommendation(recommendationId);
        return ResponseEntity.noContent().build();
    }

}
