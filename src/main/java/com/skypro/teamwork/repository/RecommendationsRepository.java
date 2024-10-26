
package com.skypro.teamwork.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;
import java.sql.Connection;

@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int getRandomTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT amount FROM transactions t WHERE t.user_id = ? LIMIT 1",
                Integer.class,
                user);
        return result != null ? result : 0;
    }

    public int getCreditTransactionsCount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM transactions t JOIN products p ON t.product_id=p.id" +
                        " WHERE p.type = 'CREDIT' AND t.user_id = ?",
                Integer.class,
                user);
        return result;
    }

    public int getDebitTransactionsCount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM transactions t JOIN products p ON t.product_id=p.id" +
                        " WHERE p.type = 'DEBIT' AND t.user_id = ?",
                Integer.class,
                user);
        return result;
    }

    public int getInvestTransactionsCount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM transactions t JOIN products p ON t.product_id=p.id" +
                        " WHERE p.type = 'INVEST' AND t.user_id = ?",
                Integer.class,
                user);
        return result;
    }

    public int getSavingDepositTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT sum(amount) FROM transactions t JOIN products p ON t.product_id=p.id" +
                        " WHERE p.type = 'SAVING' AND t.user_id = ?",
                Integer.class,
                user);
        return result;
    }

    public int getDebitDepositTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT sum(amount) FROM transactions t JOIN products p ON t.product_id=p.id" +
                        " WHERE p.type = 'DEBIT' AND t.type = 'DEPOSIT' AND t.user_id = ?",
                Integer.class,
                user);
        return result;
    }

    public int getDebitWithdrawTransactionAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT sum(amount) FROM transactions t JOIN products p ON t.product_id=p.id" +
                        " WHERE p.type = 'DEBIT' AND t.type = 'WITHDRAW' AND t.user_id = ?",
                Integer.class,
                user);
        return result;
    }

    public int getSavingDepositTransactionsAmount(UUID user) {
        Integer result = jdbcTemplate.queryForObject(
                "SELECT sum(amount) FROM transactions t JOIN products p ON t.product_id=p.id" +
                        " WHERE p.type = 'SAVING' AND t.type = 'DEPOSIT' AND t.user_id = ?",
                Integer.class,
                user);
        return result;
    }

    public boolean getUserOf(Rule rule, UUID user) {
        var type = rule.getArguments(0);
        var result = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM transactions t JOIN products p ON t.product_id=p.id" +
                        " WHERE p.type = ? AND t.user_id = ?",
                Integer.class,
                type,
                user);
        Boolean request = result >= 1;
        return request;

    }

    public boolean getActiveUserOf(Rule rule, UUID user) {
        var type = rule.getArguments(0);
        var result = jdbcTemplate.queryForObject(
                "SELECT count(*) FROM transactions t JOIN products p ON t.product_id=p.id" +
                        " WHERE p.type = ? AND t.user_id = ?",
                Integer.class,
                type,
                user);
        Boolean request = result >= 5; // число 5 добавить статическую переменную для правила
        return request;
    }

    public boolean getTransactionSumCompare(Rule rule, UUID user) {
        var type = rule.getArguments(0);
        var result = jdbcTemplate.queryForObject("SELECT sum(amount) FROM transactions t JOIN products p ON t.product_id=p.id" +
                        " WHERE p.type = ? AND t.user_id = ?",
                Integer.class,
                type,
                user);
        Boolean request = result > rule.getArgument(3);
        return request;
    }

    public boolean getTransactionSumCompareDepositWithdraw(Rule rule, UUID user) {
        var type = rule.getArguments(0);
        var comparisons = rule.getArguments(1);
        var result = jdbcTemplate.queryForObject(
                "SELECT CASE" +
                        "WHEN withdraw_sum" + comparisons + "deposit_sum THEN TRUE" +
                        "ELSE FALSE" +
                        "END AS result" +
                        "FROM (" +
                        "        SELECT" +
                        "        COUNT(CASE WHEN p.type = '" + type + "' AND t.type = 'WITHDRAW' AND t.user_id = '" + user + "' THEN 1 END) AS withdraw_sum," +
                        "COUNT(CASE WHEN p.type = '" + type + "' AND t.type = 'DEPOSIT' AND t.user_id = '" + user + "' THEN 1 END) AS deposit_sum" +
                        "FROM transactions t" +
                        "JOIN products p ON t.product_id = p.id" +
                        "WHERE t.user_id ='" + user + "') AS counts",
                Boolean.class,
                type,
                user);
        return result;


    }


}
