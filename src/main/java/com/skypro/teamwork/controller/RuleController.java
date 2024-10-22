package com.skypro.teamwork.controller;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.repository.ObjectRepository;
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
@RequestMapping("/rule")
public class RuleController {

    private final ObjectRepository repository;

    public RuleController(ObjectRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{recommendationId}")
    public ResponseEntity<Map<String, Object>> getRulesOfRecommendation(@PathVariable UUID recommendationId) {
        Recommendation recommendation = repository.findById(recommendationId);
        List<Rule> rules = recommendation.getRules();
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        data.put("id", UUID.randomUUID());
        data.put("product_name", recommendation.getName());
        data.put("product_id", recommendationId);
        data.put("product_text", recommendation.getText());
        data.put("rule", rules);
        response.put("data", data);
        return ResponseEntity.ok(response);
    }

}
