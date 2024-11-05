package com.skypro.teamwork.querysets;

import com.skypro.teamwork.repository.TransactionsRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserOfQuery implements QuerySet{

    private final TransactionsRepository transactionsRepository;

    public UserOfQuery(TransactionsRepository transactionsRepository) {
        this.transactionsRepository = transactionsRepository;
    }

    public String getQueryType() {
        return "USER_OF";
    }

    @Override
    public boolean checkRule(List<String> arguments, UUID userId) {
        return transactionsRepository.checkUserOf(userId, arguments);
    }
}
