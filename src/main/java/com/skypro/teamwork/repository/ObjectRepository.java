package com.skypro.teamwork.repository;

import com.skypro.teamwork.model.Recommendation;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ObjectRepository {

    private final Map<UUID, Recommendation> recommendations = new HashMap<>();

    public ObjectRepository() {
    }

    public Recommendation findById(UUID id) {
        return recommendations.get(id);
    }
}
