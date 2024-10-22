package com.skypro.teamwork.controller;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.repository.ObjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RuleControllerTest {

    @LocalServerPort
    private int port;

    private final UUID RECOMMENDATION_ID_1 = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");
    private final UUID RECOMMENDATION_ID_2 = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

    private final String RECOMMENDATION_NAME = "Name";

    private final String RECOMMENDATION_TEXT = "Description text";

    private final List<Rule> RULES =//Модифицировать, когда в класс Recommendation добавится поле List<Rule> rules

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    RulseService service;

    @Autowired
    ObjectRepository repository;

    @Test
    @DisplayName("should get all recommendations")
    void getRules() {
        String url = "http://localhost:" + port + "/rule/";
        Recommendation recommendationA = new Recommendation(RECOMMENDATION_ID_1, RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);//Модифицировать, когда в класс Recommendation добавится поле List<Rule> rules
        Recommendation recommendationB = new Recommendation(RECOMMENDATION_ID_2, RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);//Модифицировать, когда в класс Recommendation добавится поле List<Rule> rules
        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(recommendationA);
        recommendations.add(recommendationB);
        repository.save(recommendationA);
        repository.save(recommendationB);
        Map<String, List<Map<String, Object>>> expected = new HashMap<>();
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
        expected.put("data", data);
        HttpEntity<Map<String, List<Map<String, Object>>>> entity = new HttpEntity<>(expected);
        ResponseEntity<Map<String, List<Map<String, Object>>>> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, List<Map<String, Object>>>>() {
                }
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Map<String, List<Map<String, Object>>> actual = responseEntity.getBody();
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.get("data"));
        Assertions.assertNotNull(actual.get("data").get(0));
        Assertions.assertEquals(expected.get("data").get(0).get("id"), actual.get("data").get(0).get("id"));
        Assertions.assertEquals(expected.get("data").get(0).get("product_name"), actual.get("data").get(0).get("product_name"));
        Assertions.assertEquals(expected.get("data").get(0).get("product_id"), actual.get("data").get(0).get("product_id"));
        Assertions.assertEquals(expected.get("data").get(0).get("product_text"), actual.get("data").get(0).get("product_text"));
        Assertions.assertEquals(expected.get("data").get(0).get("rule"), actual.get("data").get(0).get("rule"));
        Assertions.assertNotNull(actual.get("data").get(1));
        Assertions.assertEquals(expected.get("data").get(1).get("id"), actual.get("data").get(1).get("id"));
        Assertions.assertEquals(expected.get("data").get(1).get("product_name"), actual.get("data").get(1).get("product_name"));
        Assertions.assertEquals(expected.get("data").get(1).get("product_id"), actual.get("data").get(1).get("product_id"));
        Assertions.assertEquals(expected.get("data").get(1).get("product_text"), actual.get("data").get(1).get("product_text"));
        Assertions.assertEquals(expected.get("data").get(1).get("rule"), actual.get("data").get(1).get("rule"));

    }

    @Test
    @DisplayName("should create recommendation")
    void createRulesOfRecommendation() {
        String url = "http://localhost:" + port + "/rule/";
        Recommendation recommendation = service.createRecommendation(RECOMMENDATION_NAME, RECOMMENDATION_ID_1, RECOMMENDATION_TEXT, RULES);
        Map<String, Object> expected = new HashMap<>();
        expected.put("id", UUID.randomUUID());
        expected.put("product_name", recommendation.getName());
        expected.put("product_id", recommendation.getId());
        expected.put("product_text", recommendation.getText());
        expected.put("rule", recommendation.getRules());
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(expected);
        ResponseEntity<Map<String, Object>> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }
        );
        Map<String, Object> actual = responseEntity.getBody();
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.get("id"));
        Assertions.assertEquals(expected.get("product_name"), actual.get("product_name"));
        Assertions.assertEquals(expected.get("product_id"), actual.get("product_id"));
        Assertions.assertEquals(expected.get("product_text"), actual.get("product_text"));
        Assertions.assertEquals(expected.get("rule"), actual.get("rule"));
    }

    @Test
    @DisplayName("should delete recommendation")
    void deleteRecommendation() {
        String url = "http://localhost:" + port + "/rule/" + RECOMMENDATION_ID_1;
        Recommendation recommendation = new Recommendation(RECOMMENDATION_ID_1, RECOMMENDATION_NAME, RECOMMENDATION_TEXT);//Модифицировать, когда в класс Recommendation добавится поле List<Rule> rules
        repository.save(recommendation);
        HttpEntity<Recommendation> expected = ResponseEntity.noContent().build();
        ResponseEntity<Recommendation> actual = testRestTemplate.exchange(
                url,
                HttpMethod.DELETE,
                expected,
                Recommendation.class
        );
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(HttpStatusCode.valueOf(204), actual.getStatusCode());
    }
}