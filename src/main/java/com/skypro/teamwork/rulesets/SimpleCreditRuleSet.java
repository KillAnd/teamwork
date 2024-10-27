package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Transaction;
import com.skypro.teamwork.repository.ObjectRepository;
import com.skypro.teamwork.repository.RecommendationsRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class SimpleCreditRuleSet implements RecommendationRuleSet{

    private final RecommendationsRepository repository;

    private final ObjectRepository recommendations;

    public SimpleCreditRuleSet(RecommendationsRepository repository, ObjectRepository recommendations) {
        this.repository = repository;
        this.recommendations = recommendations;
    }

    private final int MINIMAL_DEBIT_WITHDRAW_AMOUNT = 100_000;

    @Override
    public Optional<Recommendation> checkRuleMatching(UUID userID) {
        if (checkRuleOne(userID) && checkRuleTwo(userID) && checkRuleThree(userID)) {
            return Optional.ofNullable(recommendations.findById(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f")));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Recommendation getRecommendation() {
        return recommendations.findById(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));
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
