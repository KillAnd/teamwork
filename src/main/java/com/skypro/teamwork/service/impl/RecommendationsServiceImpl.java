package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.model.Recommendation;
import com.skypro.teamwork.repository.ObjectRepository;
import com.skypro.teamwork.rulesets.Invest500RuleSet;
import com.skypro.teamwork.rulesets.SimpleCreditRuleSet;
import com.skypro.teamwork.rulesets.TopSavingRuleSet;
import com.skypro.teamwork.service.RecommendationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
public class RecommendationsServiceImpl implements RecommendationsService {
    @Autowired
    ObjectRepository objectRepository;
    @Autowired
    Invest500RuleSet invest500RuleSet;
    @Autowired
    SimpleCreditRuleSet simpleCreditRuleSet;
    @Autowired
    TopSavingRuleSet topSavingRuleSet;

    //    Request: GET /recommendation/<user_id>
//    Response:
//            200 OK
//    Content-Type: application/json
//    {
//        "user_id": <user_id>,
//            "recommendations": [
//        {"name": <имя продукта>, "id": <id продукта>, "text": "текстовое описание продукта"},
//		...
//	]
//    }
//В случае если для данного пользователя нет рекомендаций, нужно возвращать  такой же объект, как и в случае наличия рекомендаций, но массив recommendations должен быть пустым
    public List<Recommendation> recommendationService(UUID userID) {
        List<Recommendation> result = new ArrayList<>();
        if (simpleCreditRuleSet.checkRuleMatching(userID)) {
            result.add(objectRepository.findById(UUID.fromString("ab138afb-f3ba-4a93-b74f-0fcee86d447f"))); // доделать, когда поймем, как храним варианты рекомендаций
        }
        if (invest500RuleSet.checkRuleMatching(userID)) {
            result.add(objectRepository.findById(UUID.fromString("147f6a0f-3b91-413b-ab99-87f081d60d5a"))); // доделать, когда поймем, как храним варианты рекомендаций
        }
        if (topSavingRuleSet.checkRuleMatching(userID)) {
            result.add(objectRepository.findById(UUID.fromString("59efc529-2fff-41af-baff-90ccd7402925"))); // доделать, когда поймем, как храним варианты рекомендаций
        }
        return result; // собранный ArrayList потом в контроллере перевести в json
    }
}
