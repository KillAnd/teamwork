package com.skypro.teamwork.controller;

import com.skypro.teamwork.rulesets.DynamicRecommendationImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;


@RestController
@RequestMapping("/management")
public class ManagementController {

    private final CacheManager cacheManager;
    private final Logger logger = LoggerFactory.getLogger(ManagementController.class);

    public ManagementController(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @PostMapping("/clear-caches")
    public void clear() {
        cacheManager.getCache("check_rule").clear();
        logger.info("Cache was cleared");
    }
}
