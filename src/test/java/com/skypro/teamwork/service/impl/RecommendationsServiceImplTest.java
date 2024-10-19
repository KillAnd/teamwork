package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.repository.ObjectRepository;
import com.skypro.teamwork.repository.RecommendationsRepository;
import com.skypro.teamwork.rulesets.Invest500RuleSet;
import com.skypro.teamwork.rulesets.SimpleCreditRuleSet;
import com.skypro.teamwork.rulesets.TopSavingRuleSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RecommendationsServiceImplTest {

    @Mock
    SimpleCreditRuleSet simpleCreditRuleSet;

    @Mock
    Invest500RuleSet invest500RuleSet;

    @Mock
    TopSavingRuleSet topSavingRuleSet;

    @Mock
    ObjectRepository objectRepository;

    @InjectMocks
    RecommendationsServiceImpl recommendationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testGetRecommendations_SimpleCreditRuleMatches() {
        UUID userID = UUID.randomUUID();
        UUID recommendationId = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");
        Recommendation recommendation = new Recommendation();

        when(simpleCreditRuleSet.checkRuleMatching(userID)).thenReturn(true);
        when(objectRepository.findById(recommendationId)).thenReturn(recommendation);

        List<Recommendation> result = recommendationService.recommendationService(userID);

        assertEquals(1, result.size());
        assertEquals(recommendation, result.get(0));
    }

    @Test
    void testGetRecommendations_Invest500RuleMatches() {
        UUID userID = UUID.randomUUID();
        UUID recommendationId = UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a");
        Recommendation recommendation = new Recommendation();

        when(invest500RuleSet.checkRuleMatching(userID)).thenReturn(true);
        when(objectRepository.findById(recommendationId)).thenReturn(recommendation);

        List<Recommendation> result = recommendationService.recommendationService(userID);

        assertEquals(1, result.size());
        assertEquals(recommendation, result.get(0));
    }

    @Test
    void testGetRecommendations_TopSavingRuleMatches() {
        UUID userID = UUID.randomUUID();
        UUID recommendationId = UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925");
        Recommendation recommendation = new Recommendation();

        when(topSavingRuleSet.checkRuleMatching(userID)).thenReturn(true);
        when(objectRepository.findById(recommendationId)).thenReturn(recommendation);

        List<Recommendation> result = recommendationService.recommendationService(userID);

        assertEquals(1, result.size());
        assertEquals(recommendation, result.get(0));
    }

    @Test
    void testGetRecommendations_NoRulesMatch() {
        UUID userID = UUID.randomUUID();

        when(simpleCreditRuleSet.checkRuleMatching(userID)).thenReturn(false);
        when(invest500RuleSet.checkRuleMatching(userID)).thenReturn(false);
        when(topSavingRuleSet.checkRuleMatching(userID)).thenReturn(false);

        List<Recommendation> result = recommendationService.recommendationService(userID);

        assertEquals(0, result.size());
    }
}