package com.skypro.teamwork.controller;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import com.skypro.teamwork.model.dto.RecommendationDTO;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
class RuleControllerTest {

    @LocalServerPort
    private int port;

    private final UUID RECOMMENDATION_ID_1 = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");
    private final UUID RECOMMENDATION_ID_2 = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

    private final String RECOMMENDATION_NAME = "Name";

    private final String RECOMMENDATION_TEXT = "Description text";

    private final List<Rule> RULES = new ArrayList<>(List.of(
            new Rule("USER_OF", List.of("DEBIT"), false),
            new Rule("USER_OF", List.of("INVEST"), true)
    ));

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
        Recommendation recommendationA = new Recommendation(RECOMMENDATION_ID_1, RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);
        Recommendation recommendationB = new Recommendation(RECOMMENDATION_ID_2, RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);
        List<Recommendation> recommendations = new ArrayList<>();
        recommendations.add(recommendationA);
        recommendations.add(recommendationB);
        repository.save(recommendationA);
        repository.save(recommendationB);
        Map<String, List<RecommendationDTO>> expected = new HashMap<>();
        List<RecommendationDTO> data = new ArrayList<>();
        for (Recommendation recommendation : recommendations) {
            data.add(RecommendationMapper.mapToDTO(recommendation));
        }
        expected.put("data", data);
        HttpEntity<Map<String, List<RecommendationDTO>>> entity = new HttpEntity<>(expected);
        ResponseEntity<Map<String, List<RecommendationDTO>>> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Map<String, List<RecommendationDTO>>>() {
                }
        );
        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Map<String, List<RecommendationDTO>> actual = responseEntity.getBody();
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.get("data"));
        Assertions.assertNotNull(actual.get("data").get(0));
        Assertions.assertEquals(expected.get("data").get(0).getId(), actual.get("data").get(0).getId());
        Assertions.assertEquals(expected.get("data").get(0).getName(), actual.get("data").get(0).getName());
        Assertions.assertEquals(expected.get("data").get(0).getProductId(), actual.get("data").get(0).getProductId());
        Assertions.assertEquals(expected.get("data").get(0).getText(), actual.get("data").get(0).getText());
        Assertions.assertEquals(expected.get("data").get(0).getRules(), actual.get("data").get(0).getRules());
        Assertions.assertNotNull(actual.get("data").get(1));
        Assertions.assertEquals(expected.get("data").get(1).getId(), actual.get("data").get(0).getId());
        Assertions.assertEquals(expected.get("data").get(1).getName(), actual.get("data").get(0).getName());
        Assertions.assertEquals(expected.get("data").get(1).getProductId(), actual.get("data").get(0).getProductId());
        Assertions.assertEquals(expected.get("data").get(1).getText(), actual.get("data").get(0).getText());
        Assertions.assertEquals(expected.get("data").get(1).getRules(), actual.get("data").get(0).getRules());

    }

    @Test
    @DisplayName("should create recommendation")
    void createRulesOfRecommendation() {
        String url = "http://localhost:" + port + "/rule/";
        RecommendationDTO expected = service.createRecommendation(RECOMMENDATION_NAME, RECOMMENDATION_ID_1, RECOMMENDATION_TEXT, RULES);
        HttpEntity<RecommendationDTO> entity = new HttpEntity<>(expected);
        ResponseEntity<RecommendationDTO> responseEntity = testRestTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<RecommendationDTO>() {
                }
        );
        RecommendationDTO actual = responseEntity.getBody();
        Assertions.assertNotNull(actual);
        Assertions.assertNotNull(actual.getId());
        Assertions.assertEquals(expected.getName(), actual.getName());
        Assertions.assertEquals(expected.getProductId(), actual.getProductId());
        Assertions.assertEquals(expected.getText(), actual.getText());
        Assertions.assertEquals(expected.getRules(), actual.getRules());
    }

    @Test
    @DisplayName("should delete recommendation")
    void deleteRecommendation() {
        String url = "http://localhost:" + port + "/rule/" + RECOMMENDATION_ID_1;
        Recommendation recommendation = new Recommendation(RECOMMENDATION_ID_1, RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);
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