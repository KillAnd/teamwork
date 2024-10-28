package com.skypro.teamwork.repository;

import org.springframework.stereotype.Repository;

@Repository
public class ArgumentsRepository {

    public static final String[] QUERIES = new String[]{"USER_OF", "ACTIVE_USER_OF", "TRANSACTION_SUM_COMPARE", "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW"};

    public static final String[] PRODUCT_TYPES = new String[] {"DEBIT", "CREDIT", "INVEST", "SAVING"};

    public static final String[] TRANSACTION_TYPES = new String[] {"WITHDRAW", "DEPOSIT"};

    public static final String[] RELATION_OPERATORS = new String[] {">", "<", ">=", "<=", "=="};



}
