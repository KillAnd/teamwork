package com.skypro.teamwork.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.UUID;

public class RecommendationForUserDTO {

    @JsonProperty("id")
    private UUID productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("text")
    private String text;

    public RecommendationForUserDTO() {
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationForUserDTO that = (RecommendationForUserDTO) o;
        return Objects.equals(productId, that.productId) && Objects.equals(name, that.name) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, text);
    }

    @Override
    public String toString() {
        return "RecommendationForUserDTO{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

}
