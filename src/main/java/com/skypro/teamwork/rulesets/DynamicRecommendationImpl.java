package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import com.skypro.teamwork.querysets.QuerySet;
import com.skypro.teamwork.repository.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DynamicRecommendationImpl implements DynamicRecommendation{

    private final Logger logger = LoggerFactory.getLogger(DynamicRecommendationImpl.class);

    private final List<QuerySet> querySets;

    private final TransactionsRepository transactionsRepository;

    public DynamicRecommendationImpl(List<QuerySet> querySets, TransactionsRepository transactionsRepository) {
        this.querySets = querySets;
        this.transactionsRepository = transactionsRepository;
    }

    @Autowired
    CacheManager cacheManager;

    @Cacheable(value = "check_rule",  key = "{#userId, #recommendation.id}")
    public boolean checkRuleMatching(Recommendation recommendation, UUID userId) {
        boolean result = true;
        List<Rule> rules = recommendation.getRules();
        for (Rule rule : rules) {
            result = result && checkRule(rule, userId);
        }
        return result;
    }


    protected boolean checkRule(Rule rule, UUID userId) {
        String query = rule.getQuery();
        List<String> arguments = rule.getArguments();
        for (QuerySet querySet : querySets) {
            if (querySet.getQueryType().equals(query)) {
                return querySet.checkRule(rule.getArguments(), userId) != rule.isNegate();
            }
        }
        return false;
    }
}
