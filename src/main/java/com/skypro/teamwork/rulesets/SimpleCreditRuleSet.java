package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;


@Component
public class SimpleCreditRuleSet implements RecommendationRuleSet{

    private final RecommendationsRepository repository;

    public SimpleCreditRuleSet(RecommendationsRepository repository) {
        this.repository = repository;
    }

    private final int MINIMAL_DEBIT_WITHDRAW_AMOUNT = 100_000;

    @Override
    public boolean checkRuleMatching(UUID userID) {
        return (checkRuleOne(userID) && checkRuleTwo(userID) && checkRuleThree(userID));
    }

    //Пользователь не использует продукты с типом CREDIT.
    private boolean checkRuleOne(UUID userID) {
        //Получаем количество транзакций типа CREDIT по UUID пользователя
        return repository.getCreditTransactionsCount(userID) == 0;
    }

    //Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
    private boolean checkRuleTwo(UUID userID) {
        //Получаем сумму операций пополнений типа DEBIT
        return repository.getDebitDepositTransactionAmount(userID) >
                //Получаем сумму операций трат типа DEBIT
                repository.getDebitWithdrawTransactionAmount(userID);
    }

    //Сумма трат по всем продуктам типа DEBIT больше, чем MINIMAL_DEBIT_WITHDRAW_AMOUNT ₽.
    private boolean checkRuleThree(UUID userID) {
        //Получаем сумму операций трат типа DEBIT
        return repository.getDebitWithdrawTransactionAmount(userID) > MINIMAL_DEBIT_WITHDRAW_AMOUNT;
    }
}
