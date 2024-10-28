package com.skypro.teamwork.service;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.model.Rule;
import com.skypro.teamwork.model.dto.RecommendationDTO;
import com.skypro.teamwork.model.dto.RecommendationListDTO;
import com.skypro.teamwork.model.dto.mapper.RecommendationListMapper;
import com.skypro.teamwork.model.dto.mapper.RecommendationMapper;
import com.skypro.teamwork.repository.DynamicRecommendationRepository;
import com.skypro.teamwork.repository.DynamicRulesRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RuleServiceTest {

    @Mock
    private DynamicRecommendationRepository recommendationRepository;

    @Mock
    private DynamicRulesRepository ruleRepository;

    @InjectMocks
    RuleService service;

    private List<Recommendation> recommendations;

    private final Recommendation NEW_RECOMMENDATION = new Recommendation(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"), "Top saving", "Откройте мир выгодных кредитов с нами!\n" +
            "\n" +
            "Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.\n" +
            "\n" +
            "Почему выбирают нас:\n" +
            "\n" +
            "Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.\n" +
            "\n" +
            "Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.\n" +
            "\n" +
            "Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.\n" +
            "\n" +
            "Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!",
            List.of(new Rule("USER_OF", List.of("DEBIT"), true)));

    @BeforeEach
    void init() {
        recommendations =new LinkedList<>(List.of(
                new Recommendation(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"), "Invest 500", "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"),
                new Recommendation(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"), "Simple credit", "Откройте свою собственную «Копилку» с нашим банком! «Копилка» — это уникальный банковский инструмент, который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытых чеков и потерянных квитанций — всё под контролем!\n" +
                        "\n" +
                        "Преимущества «Копилки»:\n" +
                        "\n" +
                        "Накопление средств на конкретные цели. Установите лимит и срок накопления, и банк будет автоматически переводить определенную сумму на ваш счет.\n" +
                        "\n" +
                        "Прозрачность и контроль. Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости.\n" +
                        "\n" +
                        "Безопасность и надежность. Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг.\n" +
                        "\n" +
                        "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!")
        ));

    }

    @Test
    void getAll() {
        //given
        RecommendationListDTO expected = RecommendationListMapper.mapToDTO(recommendations);
        //when
        when(recommendationRepository.findAll()).thenReturn(recommendations);
        //then
        RecommendationListDTO actual = service.getAll();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createRecommendation() {
        //given
        recommendations.add(NEW_RECOMMENDATION);
        RecommendationDTO expected = RecommendationMapper.mapToDTO(NEW_RECOMMENDATION);
        //when
        when(recommendationRepository.save(NEW_RECOMMENDATION)).thenReturn(NEW_RECOMMENDATION);
        //then
        RecommendationDTO actual = service.createRecommendation(expected).get();
        Assertions.assertEquals(expected, actual);
        Assertions.assertEquals(recommendations.get(2), RecommendationMapper.mapFromDTO(actual));
    }
}