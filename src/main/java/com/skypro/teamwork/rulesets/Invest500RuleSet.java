package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.repository.RecommendationsRepository;
import com.skypro.teamwork.object.Recommendation;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class Invest500RuleSet implements RecommendationRuleSet{

    private final RecommendationsRepository repository;

    public Invest500RuleSet(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Recommendation> checkRuleMatching(UUID userID) {
        //Пользователь использует как минимум один продукт с типом DEBIT.
        boolean firstRuleMatch = false;
        //Получаем количество транзакций типа DEBIT по UUID пользователя
        int debitTransactionsCount = repository.getDebitTransactionsCount(userID);
        if (debitTransactionsCount > 0) {
            firstRuleMatch = true;
        }
        //Пользователь не использует продукты с типом INVEST.
        boolean secondRuleMatch = false;
        //Получаем количество транзакций типа INVEST по UUID пользователя
        int investTransactionsCount = repository.getInvestTransactionsCount(userID);
        if (investTransactionsCount == 0) {
            secondRuleMatch = true;
        }
        //Сумма пополнений продуктов с типом SAVING больше 1000 ₽.
        boolean thirdRuleMatch = false;
        //Получаем сумму пополнений продуктов с типом SAVING
        int savingTransactionAmount = repository.getSavingDepositTransactionAmount(userID);
        if (savingTransactionAmount > 1000) {
            thirdRuleMatch = true;
        }
        if (firstRuleMatch&&secondRuleMatch&&thirdRuleMatch) {
            //Если выполняются все 3 условия, возвращаем рекомендацию "Invest 500"
            return repository.getRecommendationByID(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
        } else {
            //Если хотя бы одно не выполняется, возвращаем null
            return null;
        }

    }
}
