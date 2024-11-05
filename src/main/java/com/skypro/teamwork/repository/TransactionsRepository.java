package com.skypro.teamwork.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class TransactionsRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean checkUserOf(UUID userId, List<String> arguments) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT CAST (CASE WHEN (SELECT COUNT(*) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = ? AND t.user_id = ?) > 0 THEN 1 ELSE 0 END AS BIT)",
                boolean.class,
                arguments.get(0),
                userId
        ));
    }

    public boolean checkActiveUserOf(UUID userId, List<String> arguments) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT CAST (CASE WHEN (SELECT COUNT(*) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = ? AND t.user_id = ?) > 5 THEN 1 ELSE 0 END AS BIT)",
                boolean.class,
                arguments.get(0),
                userId
        ));
    }

    public boolean checkTransactionSumCompare(UUID userId, List<String> arguments) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT CAST (CASE WHEN (SELECT SUM(amount) FROM transactions t JOIN products p ON t.product_id=p.id WHERE p.type = ? AND t.type = ? AND t.user_id = ?) " + arguments.get(2) +" ? THEN 1 ELSE 0 END AS BIT)",
                boolean.class,
                arguments.get(0),
                arguments.get(1),
                userId,
                Integer.valueOf(arguments.get(3))
        ));
    }

    public boolean checkTransactionSumCompareDepositWithdraw(UUID userId, List<String> arguments) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
                "SELECT CAST (CASE WHEN (SELECT SUM(amount) FROM transactions t " +
                        "JOIN products p ON t.product_id=p.id WHERE p.type = ? AND t.type = 'DEPOSIT' " +
                        "AND t.user_id = ?) " + arguments.get(1) + "(SELECT SUM(amount) FROM transactions" +
                        " t JOIN products p ON t.product_id=p.id WHERE p.type = ? AND t.type = " +
                        "'WITHDRAW' AND t.user_id = ?) THEN 1 ELSE 0 END AS BIT)",
                boolean.class,
                arguments.get(0),
                userId,
                arguments.get(0),
                userId
        ));
    }

}
