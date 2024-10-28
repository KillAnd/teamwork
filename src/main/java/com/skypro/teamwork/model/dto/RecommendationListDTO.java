package com.skypro.teamwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class RecommendationListDTO {
    @JsonProperty("data")
    private List<RecommendationDTO> data;

    public RecommendationListDTO() {
    }

    public List<RecommendationDTO> getData() {
        return data;
    }

    public void setData(List<RecommendationDTO> data) {
        this.data = data;
    }
}