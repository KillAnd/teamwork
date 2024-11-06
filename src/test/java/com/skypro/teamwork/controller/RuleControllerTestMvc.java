package com.skypro.teamwork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import com.skypro.teamwork.model.dto.mapper.RecommendationMapper;
import com.skypro.teamwork.service.RuleService;
import com.skypro.teamwork.model.dto.RecommendationDTO;
import com.skypro.teamwork.model.dto.RecommendationListDTO;
import com.skypro.teamwork.model.dto.mapper.RecommendationListMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RuleController.class)
public class RuleControllerTestMvc {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RuleService service;

    private final UUID RECOMMENDATION_ID = UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f");

    private final String RECOMMENDATION_NAME = "Name";

    private final String RECOMMENDATION_TEXT = "Description text";

    private final List<Rule> RULES = new ArrayList<>(List.of(
            new Rule("USER_OF", List.of("DEBIT"), false),
            new Rule("USER_OF", List.of("INVEST"), true)
    ));

    @Test
    @DisplayName("Should get all recommendations")
    void getTest() throws Exception {
        List<Recommendation> expected = new ArrayList<>();
        Recommendation rec1 = new Recommendation(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);
        Recommendation rec2 = new Recommendation(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);
        rec1.setId(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
        rec2.setId(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));
        expected.add(rec1);
        expected.add(rec2);
        RecommendationListDTO actual = RecommendationListMapper.mapToDTO(expected);
        when(service.getAll()).thenReturn(actual);
        ResultActions perform = mockMvc.perform(get("/rule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expected)));
        perform.andExpect(jsonPath("data[0].id").exists())
                .andExpect(jsonPath("data[0].product_name").value(expected.get(0).getName()))
                .andExpect(jsonPath("data[0].product_text").value(expected.get(0).getText()))
                .andExpect(jsonPath("data[0].product_id").value(expected.get(0).getProductId().toString()))
                .andExpect(jsonPath("data[0].rule[0].query").value(expected.get(0).getRules().get(0).getQuery()))
                .andExpect(jsonPath("data[0].rule[0].arguments[0]").value(expected.get(0).getRules().get(0).getArguments().get(0)))
                .andExpect(jsonPath("data[0].rule[0].negate").value(expected.get(0).getRules().get(0).isNegate()))
                .andExpect(jsonPath("data[1].id").exists())
                .andExpect(jsonPath("data[1].product_name").value(expected.get(1).getName()))
                .andExpect(jsonPath("data[1].product_text").value(expected.get(1).getText()))
                .andExpect(jsonPath("data[1].product_id").value(expected.get(1).getProductId().toString()))
                .andExpect(jsonPath("data[1].rule[0].query").value(expected.get(1).getRules().get(0).getQuery()))
                .andExpect(jsonPath("data[1].rule[0].arguments[0]").value(expected.get(1).getRules().get(0).getArguments().get(0)))
                .andExpect(jsonPath("data[1].rule[0].negate").value(expected.get(1).getRules().get(0).isNegate()))
                .andDo(print());
    }

    @Test
    @DisplayName("Should post a recommendation")
    void postTest() throws Exception {
        Recommendation rec = new Recommendation(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);
        RecommendationDTO expected = RecommendationMapper.mapToDTO(rec);
        when(service.createRecommendation(RecommendationMapper.mapToDTO(rec))).thenReturn(Optional.of(expected));
        mockMvc.perform(post("/rule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expected)))
                //.andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.product_name").value(expected.getName()))
                .andExpect(jsonPath("$.product_text").value(expected.getText()))
                .andExpect(jsonPath("$.product_id").value(expected.getProductId().toString()))
                .andExpect(jsonPath("$.rule[0].query").value(expected.getRules().get(0).getQuery()))
                .andExpect(jsonPath("$.rule[0].arguments[0]").value(expected.getRules().get(0).getArguments().get(0)))
                .andExpect(jsonPath("$.rule[0].negate").value(expected.getRules().get(0).isNegate()))
                .andDo(print());
    }

    @Test
    @DisplayName("Should response bad request")
    void postBadTest() throws Exception {
        Recommendation rec = new Recommendation(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"),RECOMMENDATION_NAME, RECOMMENDATION_TEXT, RULES);
        rec.setId(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
        RecommendationDTO expected = RecommendationMapper.mapToDTO(rec);
        when(service.createRecommendation(expected)).thenReturn(null);
        ResultActions perform = mockMvc.perform(post("/rule")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(expected)));
        perform.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("Should response no content")
    void deleteTest() throws Exception {
        ResultActions perform = mockMvc.perform(delete("/rule/147f6a0f-3b91-413b-ab99-87f081d60d5a")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString("147f6a0f-3b91-413b-ab99-87f081d60d5a")));
        perform.andExpect(status().isNoContent())
                .andDo(print());
    }

}