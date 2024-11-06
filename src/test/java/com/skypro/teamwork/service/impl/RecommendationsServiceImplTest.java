package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.repository.ObjectRepository;
import com.skypro.teamwork.repository.RecommendationsRepository;
import com.skypro.teamwork.rulesets.Invest500RuleSet;
import com.skypro.teamwork.rulesets.RecommendationRuleSet;
import com.skypro.teamwork.rulesets.SimpleCreditRuleSet;
import com.skypro.teamwork.rulesets.TopSavingRuleSet;
import com.skypro.teamwork.service.RecommendationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RecommendationsServiceImplTest {

//    @Mock
//    private List<RecommendationRuleSet> ruleSets;
//
//    @InjectMocks
//    private RecommendationsServiceImpl recommendationService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testGetRecommendations_SingleRuleSetMatches() {
//        UUID userID = UUID.randomUUID();
//        RecommendationRuleSet ruleSet = mock(RecommendationRuleSet.class);
//        Recommendation recommendation = new Recommendation();
//
//        when(ruleSet.checkRuleMatching(userID)).thenReturn(Optional.of(recommendation));
//        when(ruleSet.getRecommendation()).thenReturn(recommendation);
//
//        when(ruleSets.iterator()).thenReturn(List.of(ruleSet).iterator());
//
//        List<Recommendation> result = recommendationService.recommendationService(userID);
//
//        assertEquals(1, result.size());
//        assertEquals(recommendation, result.get(0));
//    }
//
//    @Test
//    void testGetRecommendations_MultipleRuleSetsMatch() {
//        UUID userID = UUID.randomUUID();
//        RecommendationRuleSet ruleSet1 = mock(RecommendationRuleSet.class);
//        RecommendationRuleSet ruleSet2 = mock(RecommendationRuleSet.class);
//        Recommendation recommendation1 = new Recommendation();
//        Recommendation recommendation2 = new Recommendation();
//
//        when(ruleSet1.checkRuleMatching(userID)).thenReturn(Optional.of(recommendation1));
//        when(ruleSet1.getRecommendation()).thenReturn(recommendation1);
//
//        when(ruleSet2.checkRuleMatching(userID)).thenReturn(Optional.of(recommendation2));
//        when(ruleSet2.getRecommendation()).thenReturn(recommendation2);
//
//        when(ruleSets.iterator()).thenReturn(List.of(ruleSet1, ruleSet2).iterator());
//
//        List<Recommendation> result = recommendationService.recommendationService(userID);
//
//        assertEquals(2, result.size());
//        assertEquals(recommendation1, result.get(0));
//        assertEquals(recommendation2, result.get(1));
//    }
//
//    @Test
//    void testGetRecommendations_NoRulesMatch() {
//        UUID userID = UUID.randomUUID();
//        RecommendationRuleSet ruleSet = mock(RecommendationRuleSet.class);
//
//        when(ruleSet.checkRuleMatching(userID)).thenReturn(Optional.empty());
//
//        when(ruleSets.iterator()).thenReturn(List.of(ruleSet).iterator());
//
//        List<Recommendation> result = recommendationService.recommendationService(userID);
//
//        assertEquals(0, result.size());
//    }
}