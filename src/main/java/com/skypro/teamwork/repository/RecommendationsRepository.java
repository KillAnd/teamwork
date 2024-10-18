package com.skypro.teamwork.repository;

import com.skypro.teamwork.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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

    public List<UUID> readyInvest5000() {
        List<UUID> result = jdbcTemplate.queryForObject(
                "SELECT t.user_id" +
                        "FROM transactions t" +
                        "JOIN products p ON t.product_id = p.id" +
                        "WHERE p.TYPE NOT IN ('INVEST')" +
                        "GROUP BY t.USER_ID" +
                        "HAVING" +
                        "SUM(CASE WHEN p.type = 'INVEST' THEN 1 ELSE 0 END) = 0 AND" +
                        "SUM(CASE WHEN p.type = 'DEBIT' THEN 1 ELSE 0 END) > 0 AND" +
                        "SUM(CASE WHEN p.type = 'SAVING' THEN t.amount ELSE 0 END) > 1000",
                List.class);
        return result;
    }

    public List<UUID> readyTopSaving() {
        List<UUID> result = jdbcTemplate.queryForObject(
                "SELECT t.user_id" +
                        "FROM transactions t" +
                        "JOIN products p ON t.product_id = p.id" +
                        "GROUP BY t.USER_ID" +
                        "HAVING" +
                        "SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) >= 50000" +
                        "OR SUM(CASE WHEN p.type = 'SAVING' THEN t.amount ELSE 0 END) >= 50000" +
                        "AND SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) >" +
                        "SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END)" +
                        "AND SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) > 0",
                List.class);
        return result;
    }

    public List<UUID> readyCredit() {
        List<UUID> result = jdbcTemplate.queryForObject(
                "SELECT t.user_id" +
                        "FROM transactions t" +
                        "JOIN products p ON t.product_id = p.id" +
                        "GROUP BY t.USER_ID" +
                        "HAVING" +
                        "SUM(CASE WHEN p.type = 'INVEST' THEN 1 ELSE 0 END) = 0 AND" +
                        "SUM(CASE WHEN p.type = 'DEBIT' THEN t.amount ELSE 0 END) > 100000" +
                        "AND SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'DEPOSIT' THEN t.amount ELSE 0 END) >" +
                        "SUM(CASE WHEN p.type = 'DEBIT' AND t.type = 'WITHDRAW' THEN t.amount ELSE 0 END)",
                List.class);
        return result;
    }




}
