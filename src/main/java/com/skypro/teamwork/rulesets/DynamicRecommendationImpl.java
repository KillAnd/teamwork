package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
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

    private final TransactionsRepository transactionsRepository;

    public DynamicRecommendationImpl(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    @Autowired
    CacheManager cacheManager;

    public boolean checkRuleMatching(Recommendation recommendation, UUID userId) {
        boolean result = true;
        List<Rule> rules = recommendation.getRules();
        for (Rule rule : rules) {
            result = result && checkRule(rule, userId);
        }
        return result;
    }

    @Cacheable(value = "check_rule",  key = "{#userId, #rule.id}")
    protected boolean checkRule(Rule rule, UUID userId) {
        boolean result;
        String query = rule.getQuery();
        List<String> arguments = rule.getArguments();
        result = switch (query) {
            case "USER_OF" -> transactionsRepository.checkUserOf(userId, arguments, 0);
            case "ACTIVE_USER_OF" -> transactionsRepository.checkUserOf(userId, arguments, 5);
            case "TRANSACTION_SUM_COMPARE" -> transactionsRepository.checkTransactionSumCompare(userId, arguments);
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" ->
                    transactionsRepository.checkTransactionSumCompareDepositWithdraw(userId, arguments);
            default -> false;
        };
        return rule.isNegate() != result;
    }
}
