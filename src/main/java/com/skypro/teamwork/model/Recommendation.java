package com.skypro.teamwork.model;

import org.springframework.data.annotation.Id;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Recommendation {
    @Id
    private UUID id;
    private String name;
    private String text;

    private List<Rule> rules;

    public UUID getId() {
        return id;
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

    public Recommendation() {
    }

    public Recommendation(UUID id, String name, String text, List<Rule> rules) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.rules = rules;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
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
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(text, that.text) && Objects.equals(rules, that.rules);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text, rules);
    }
}
