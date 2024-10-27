package com.skypro.teamwork.model;

import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "recommendations")
public class Recommendation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @UuidGenerator
    private UUID Id;
    private UUID productId;
    private String name;
    private String text;
    @OneToMany(mappedBy = "recommendation")
    private List<Rule> rules;

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

    public UUID getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public Recommendation() {
    }

    public Recommendation(UUID productId, String name, String text, List<Rule> rules) {
        this.productId = productId;
        this.name = name;
        this.text = text;
        this.rules = rules;
    }

    public Recommendation(UUID productId, String name, String text) {
        this.productId = productId;
        this.name = name;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + productId +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", rules=" + rules +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(productId, that.productId) && Objects.equals(name, that.name) && Objects.equals(text, that.text) && Objects.equals(rules, that.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, name, text, rules);
    }
}
