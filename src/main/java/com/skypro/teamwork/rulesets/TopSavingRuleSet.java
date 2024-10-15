package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.repository.RecommendationsRepository;
import com.skypro.teamwork.object.Recommendation;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class TopSavingRuleSet implements RecommendationRuleSet{

    private final RecommendationsRepository repository;

    public TopSavingRuleSet(RecommendationsRepository repository) {
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
        /*Сумма пополнений по всем продуктам типа DEBIT больше или равна 50 000 ₽ ИЛИ
        Сумма пополнений по всем продуктам типа SAVING больше или равна 50 000 ₽.*/
        boolean secondRuleMatch = false;
        //Получаем сумму пополнений по всем продуктам типа DEBIT
        int debitDepositAmount = repository.getDebitDepositTransactionAmount(userID);
        //Получаем сумму пополнений по всем продуктам типа SAVING
        int savingDepositAmount = repository.getSavingDepositTransactionsAmount(userID);
        if (debitDepositAmount >= 50_000 || savingDepositAmount >= 50_000) {
            secondRuleMatch = true;
        }
        //Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
        boolean thirdRuleMatch = false;
        //Получаем сумму операций трат типа DEBIT
        int debitWithdrawAmount = repository.getDebitWithdrawTransactionAmount(userID);
        if (debitDepositAmount > debitWithdrawAmount) {
            thirdRuleMatch = true;
        }
        if (firstRuleMatch&&secondRuleMatch&&thirdRuleMatch) {
            //Если выполняются все 3 условия, возвращаем рекомендацию "Top saving"
            return repository.getRecommendationByID(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"));
        } else {
            //Если хотя бы одно не выполняется, возвращаем null
            return null;
        }

    }
}
