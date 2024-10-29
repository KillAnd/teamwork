package com.skypro.teamwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationListDTO that = (RecommendationListDTO) o;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}