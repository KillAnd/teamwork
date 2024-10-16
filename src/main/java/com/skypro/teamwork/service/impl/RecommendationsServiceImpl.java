package com.skypro.teamwork.service.impl;

import com.skypro.teamwork.rulesets.SimpleCreditRuleSet;
import com.skypro.teamwork.service.RecommendationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
@Service
public class RecommendationServiceImpl implements RecommendationService {
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
    public ArrayList<Recommendation> RecommendationService(UUID userID) {
        ArrayList<Recommendation> result = new ArrayList<Recommendation>();
        if (SimpleCreditRuleSet.checkRuleMatching(UUID userID)) {
            result.add(Recommendation); // доделать, когда поймем, как храним варианты рекомендаций
        }
        if (Invest500.checkRuleMatching(UUID userID)) {
            result.add(Recommendation); // доделать, когда поймем, как храним варианты рекомендаций
        }
        if (TopSaving.checkRuleMatching(UUID userID)) {
            result.add(Recommendation); // доделать, когда поймем, как храним варианты рекомендаций
        }
        return result; // собранный ArrayList потом в контроллере перевести в json
    }
}
