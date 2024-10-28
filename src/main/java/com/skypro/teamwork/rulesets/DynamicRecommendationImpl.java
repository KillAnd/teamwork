package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import com.skypro.teamwork.repository.TransactionsRepository;

import java.util.List;
import java.util.UUID;

public class DynamicRecommendationImpl implements DynamicRecommendation{

    private final TransactionsRepository transactionsRepository;

    public DynamicRecommendationImpl(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public boolean checkRuleMatching(Recommendation recommendation, UUID userId) {
        boolean result = true;
        List<Rule> rules = recommendation.getRules();
        for (Rule rule : rules) {
            result = result && checkRule(rule, userId);
        }
        return result;
    }

    protected boolean checkRule(Rule rule, UUID userId) {
        boolean result;
        String query = rule.getQuery();
        List<String> arguments = rule.getArguments();
        result = switch (query) {
            case "USER_OF" -> transactionsRepository.checkUserOf(userId, arguments);
            case "ACTIVE_USER_OF" -> transactionsRepository.checkActiveUserOf(userId, arguments);
            case "TRANSACTION_SUM_COMPARE" -> transactionsRepository.checkTransactionSumCompare(userId, arguments);
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" ->
                    transactionsRepository.checkTransactionSumCompareDepositWithdraw(userId, arguments);
            default -> false;
        };
        return rule.isNegate() != result;
    }
}
