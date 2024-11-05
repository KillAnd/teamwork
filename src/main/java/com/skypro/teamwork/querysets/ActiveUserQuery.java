package com.skypro.teamwork.querysets;

import com.skypro.teamwork.repository.TransactionsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ActiveUserQuery implements QuerySet{

    private final TransactionsRepository transactionsRepository;

    public ActiveUserQuery(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public String getQueryType() {
        return "ACTIVE_USER_OF";
    }

    @Override
    public boolean checkRule(List<String> arguments, UUID userId) {
        return transactionsRepository.checkActiveUserOf(userId, arguments);
    }
}
