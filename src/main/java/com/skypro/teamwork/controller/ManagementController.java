package com.skypro.teamwork.controller;

import com.skypro.teamwork.rulesets.DynamicRecommendationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/management")
public class ManagementController {

    private final CacheManager cacheManager;
    private final Logger logger = LoggerFactory.getLogger(ManagementController.class);

    public ManagementController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Autowired
    private BuildProperties buildInfo;

    @GetMapping("/info")
    public ResponseEntity<List<String>> getBuildInfo() {
        List<String> response = new ArrayList<>();
        response.add(buildInfo.getName());
        response.add(buildInfo.getVersion());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/clear-caches")
    public void clear() {
        cacheManager.getCache("check_rule").clear();
        logger.info("Cache was cleared");
    }
}
