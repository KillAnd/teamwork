package com.skypro.teamwork.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;
import java.util.UUID;

public class Recommendation {
    @Id
    UUID id;
    String name;
    String text;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Recommendation(UUID id, String name, String text) {
        this.id = id;
        this.name = name;
        this.text = text;
    }
    public Recommendation() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recommendation that = (Recommendation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text);
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
