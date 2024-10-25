package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.repository.RecommendationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.skypro.teamwork.model.Rule;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationRuleSetImpl implements RecommendationRuleSetInterface{

    @Autowired
    RecommendationsRepository repository;

    @Override
    public Optional<Recommendation> checkRuleMatching(UUID userId, Recommendation recommendation) {
        boolean ruleMatches = true;
        List<Rule> rules = recommendation.getRules();//получаем все правила из рекомендации
        for (Rule rule : rules) {
            ruleMatches = ruleMatches && checkRule(rule, userId);//каждое правило отрабатывает запрос SQL через репозиторий,
            // проводит логическое И с остальными результатами
        }
        if (ruleMatches) {
            return Optional.of(recommendation);
        } else return null;
    }

    private boolean checkRule(Rule rule, UUID userId) {
        boolean result;
        String query = rule.getQuery();//тип запроса, в зависимости от которого используются разные методы репозитория
        result = switch (query) {
            case "USER_OF" -> repository.checkUserOf(userId, rule);
            case "ACTIVE_USER_OF" -> repository.checkActiveUserOf(userId, rule);
            case "TRANSACTION_SUM_COMPARE" -> repository.checkTransactionSumCompare(userId, rule);
            case "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW" ->
                    repository.checkTransactionSumCompareDepositWithdraw(userId, rule);
            default -> false;
        };
        return rule.isNegate() != result;// если negate == true , то result инвертируется
    }
}