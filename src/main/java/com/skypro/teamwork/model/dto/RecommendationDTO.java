package com.skypro.teamwork.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skypro.teamwork.model.Rule;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RecommendationDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @UuidGenerator
    private UUID id;

    @JsonProperty("product_name")
    private String name;

    @JsonProperty("product_id")
    private UUID productId;

    @JsonProperty("product_text")
    private String text;

    @JsonProperty("rule")
    private List<Rule> rules;

    public RecommendationDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public UUID getId() {
        return id;
    }

    @JsonProperty
    public void setId(UUID id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecommendationDTO that = (RecommendationDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(productId, that.productId) && Objects.equals(text, that.text) && Objects.equals(rules, that.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, productId, text, rules);
    }

    @Override
    public String toString() {
        return "RecommendationDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productId=" + productId +
                ", text='" + text + '\'' +
                ", rules=" + rules +
                '}';
    }
}
