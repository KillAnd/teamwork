package com.skypro.teamwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

public class RecommendationStatsDTO {

    @JsonProperty("product_id")
    private UUID productId;

    @JsonProperty("count")
    private int counter;

    public RecommendationStatsDTO() {
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationStatsDTO that = (RecommendationStatsDTO) o;
        return Objects.equals(productId, that.productId) && Objects.equals(counter, that.counter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, counter);
    }

    @Override
    public String toString() {
        return "RecommendationStatsDTO{" +
                "productId=" + productId +
                ", counter=" + counter +
                '}';
    }
}