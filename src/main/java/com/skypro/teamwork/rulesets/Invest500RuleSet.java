package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class Invest500RuleSet implements RecommendationRuleSet{

    private final RecommendationsRepository repository;

    public Invest500RuleSet(RecommendationsRepository repository) {
        this.repository = repository;
    }

    private final int MINIMAL_SAVING_DEPOSIT_AMOUNT = 1000;

    @Override
    public boolean checkRuleMatching(UUID userID) {
        return (checkRuleOne(userID) && checkRuleTwo(userID) && checkRuleThree(userID));
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
