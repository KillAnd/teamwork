package com.skypro.teamwork.querysets;

import com.skypro.teamwork.repository.TransactionsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TransactionSumCompare implements QuerySet{

    private final TransactionsRepository transactionsRepository;

    public TransactionSumCompare(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public String getQueryType() {
        return "TRANSACTION_SUM_COMPARE";
    }

    @Override
    public boolean checkRule(List<String> arguments, UUID userId) {
        return transactionsRepository.checkTransactionSumCompare(userId, arguments);
    }
}
