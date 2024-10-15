package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.repository.RecommendationsRepository;
import com.skypro.teamwork.object.Recommendation;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class SimpleCreditRuleSet implements RecommendationRuleSet{

    private final RecommendationsRepository repository;

    public SimpleCreditRuleSet(RecommendationsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Recommendation> checkRuleMatching(UUID userID) {
        //Пользователь не использует продукты с типом CREDIT.
        boolean firstRuleMatch = false;
        //Получаем количество транзакций типа CREDIT по UUID пользователя
        int creditTransactionsCount = repository.getCreditTransactionsCount(userID);
        if (creditTransactionsCount == 0) {
            firstRuleMatch = true;
        }
        //Сумма пополнений по всем продуктам типа DEBIT больше, чем сумма трат по всем продуктам типа DEBIT.
        boolean secondRuleMatch = false;
        //Получаем количество операций пополнений типа DEBIT
        int debitDepositCount = repository.getDebitDepositTransactionCount(userID);
        //Получаем количество операций трат типа DEBIT
        int debitWithdrawCount = repository.getDebitWithdrawTransactionCount(userID);
        if (debitDepositCount > debitWithdrawCount) {
            secondRuleMatch = true;
        }
        //Сумма трат по всем продуктам типа DEBIT больше, чем 100 000 ₽.
        boolean thirdRuleMatch = false;
        //Получаем количество трат типа DEBIT в рублях
        int debitWithdrawAmount = repository.getDebitWithdrawAmount(userID);
        if (debitWithdrawAmount > 100_000) {
            thirdRuleMatch = true;
        }
        if (firstRuleMatch&&secondRuleMatch&&thirdRuleMatch) {
            //Если выполняются все 3 условия, возвращаем рекомендацию "Простой кредит"
            return repository.getRecommendationByID(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"));
        } else {
            //Если хотя бы одно не выполняется, возвращаем null
            return null;
        }

    }
}
