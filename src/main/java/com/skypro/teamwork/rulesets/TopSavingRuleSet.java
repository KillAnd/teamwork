package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class TopSavingRuleSet implements RecommendationRuleSet{

    private final RecommendationsRepository repository;

    public TopSavingRuleSet(RecommendationsRepository repository) {
        this.repository = repository;
    }

    private final int MINIMAL_DEBIT_DEPOSIT_AMOUNT = 50_000;

    private final int MINIMAL_SAVING_DEPOSIT_AMOUNT = 50_000;

    @Override
    public boolean checkRuleMatching(UUID userID) {
        return (checkRuleOne(userID) && checkRuleTwo(userID) && checkRuleThree(userID));
    }

    //Пользователь использует как минимум один продукт с типом DEBIT.
    private boolean checkRuleOne(UUID userID) {
        //Получаем количество транзакций типа DEBIT по UUID пользователя
        return repository.getDebitTransactionsCount(userID) > 0;
    }

    /*Сумма пополнений по всем продуктам типа DEBIT больше или равна MINIMAL_DEBIT_DEPOSIT_AMOUNT ₽ ИЛИ
        Сумма пополнений по всем продуктам типа SAVING больше или равна MINIMAL_SAVING_DEPOSIT_AMOUNT ₽.*/
    private boolean checkRuleTwo(UUID userID) {
        //Получаем сумму пополнений по всем продуктам типа DEBIT
        return repository.getDebitDepositTransactionAmount(userID) >= MINIMAL_DEBIT_DEPOSIT_AMOUNT ||
        //Получаем сумму пополнений по всем продуктам типа SAVING
                repository.getSavingDepositTransactionsAmount(userID) >= MINIMAL_SAVING_DEPOSIT_AMOUNT;
    }

    //Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
    private boolean checkRuleThree(UUID userID) {
        //Получаем сумму пополнений по всем продуктам типа DEBIT
        return repository.getDebitDepositTransactionAmount(userID) >
                //Получаем сумму операций трат типа DEBIT
                repository.getDebitWithdrawTransactionAmount(userID);
    }
}
