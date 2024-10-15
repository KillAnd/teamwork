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

    private final int MINIMAL_SAVING_DEPOSIT_AMOUNT = 1000;

    @Override
    public Optional<Recommendation> checkRuleMatching(UUID userID) {
        if (checkRuleOne(userID) && checkRuleTwo(userID) && checkRuleThree(userID)) {
            //Если выполняются все 3 условия, возвращаем рекомендацию "Invest 500"
            return repository.getRecommendationByID(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"));
        } else {
            //Если хотя бы одно не выполняется, возвращаем null
            return null;
        }
    }

    //Пользователь использует как минимум один продукт с типом DEBIT.
    private boolean checkRuleOne(UUID userID) {
        //Получаем количество транзакций типа DEBIT по UUID пользователя
        return repository.getDebitTransactionsCount(userID) > 0;
    }

    //Пользователь не использует продукты с типом INVEST.
    private boolean checkRuleTwo(UUID userID) {
        //Получаем количество транзакций типа INVEST по UUID пользователя
        return repository.getInvestTransactionsCount(userID) == 0;
    }

    //Сумма пополнений продуктов с типом SAVING больше чем MINIMAL_SAVING_DEPOSIT_AMOUNT ₽.
    private boolean checkRuleThree(UUID userID) {
        //Получаем сумму пополнений продуктов с типом SAVING
        return repository.getSavingDepositTransactionAmount(userID) > MINIMAL_SAVING_DEPOSIT_AMOUNT;
    }
}
