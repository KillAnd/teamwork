package com.skypro.teamwork.rulesets;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import com.skypro.teamwork.repository.TransactionsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DynamicRecommendationImplTest {
    @Mock
    private  TransactionsRepository transactionsRepository;

    @InjectMocks
    DynamicRecommendationImpl testSubject;

    @Test
    void checkRuleMatching() {
//        //given
//        UUID userId = UUID.randomUUID();
//        Rule one = new Rule("USER_OF", List.of("DEBIT"), true);
//        Rule two = new Rule("ACTIVE_USER_OF", List.of("INVEST"), false);
//        Rule three = new Rule("TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW", List.of("SAVING", ">"), false);
//        Rule four = new Rule("TRANSACTION_SUM_COMPARE", List.of("SAVING", "DEPOSIT", ">", "25000"), false);
//        List<Rule> rules = new LinkedList<>();
//        rules.add(one);
//        rules.add(two);
//        rules.add(three);
//        rules.add(four);
//        Recommendation recommendation = new Recommendation(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), "Invest 500", "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!", rules);
//        //when
//        when(transactionsRepository.checkUserOf(userId, one.getArguments())).thenReturn(false);
//        when(transactionsRepository.checkActiveUserOf(userId, two.getArguments())).thenReturn(true);
//        when(transactionsRepository.checkTransactionSumCompareDepositWithdraw(userId, three.getArguments())).thenReturn(true);
//        when(transactionsRepository.checkTransactionSumCompare(userId, four.getArguments())).thenReturn(true);
//        boolean expected = true;
//        //then
//        boolean actual = testSubject.checkRuleMatching(recommendation, userId);
//        Assertions.assertEquals(expected, actual);

    }
}