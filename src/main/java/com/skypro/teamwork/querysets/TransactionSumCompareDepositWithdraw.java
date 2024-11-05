package com.skypro.teamwork.querysets;

import com.skypro.teamwork.repository.TransactionsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionSumCompareDepositWithdraw implements QuerySet{

    private final TransactionsRepository transactionsRepository;

    public TransactionSumCompareDepositWithdraw(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public String getQueryType() {
        return "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";
    }

    @Override
    public boolean checkRule(List<String> arguments, UUID userId) {
        return transactionsRepository.checkTransactionSumCompareDepositWithdraw(userId, arguments);
    }
}
