package com.skypro.teamwork.service;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.skypro.teamwork.model.dto.RecommendationForUserDTO;
import com.skypro.teamwork.repository.TransactionsRepository;
import com.skypro.teamwork.service.RecommendationsService;

import java.util.*;

public class TelegramBotServiceImpl {

    private final RecommendationsService recommendationService;

    private final TransactionsRepository transactionsRepository;

    public TelegramBotServiceImpl(RecommendationsService recommendationService, TransactionsRepository transactionsRepository) {
        this.recommendationService = recommendationService;
        this.transactionsRepository = transactionsRepository;
    }

    public SendMessage updateMessage(Message message) {
        if (message.text().equals("/start")) {
            return startMessage(message);
        } else if (message.text().startsWith("/recommend ")) {
            return recommendationMessage(message);
        } else {
            return notAvailableMessage(message);
        }
    }

    private SendMessage startMessage(Message message) {
        String name = message.chat().firstName() + " " + message.chat().lastName();
        String messageText = "Здравствуйте " + name + "!\n" +
                "Отправляйте команду\n/recommend {имя_пользователя}\nдля получения рекомендаций по нашим продуктам";
        return new SendMessage(message.chat().id(), messageText);
    }

    private SendMessage notAvailableMessage(Message message) {
        return new SendMessage(message.chat().id(), "Неверная команда");
    }

    private SendMessage recommendationMessage(Message message) {
        String messageText;
        String userName = message.text().substring("/recommend ".length());
        List<String> listOfUserInfo = transactionsRepository.getUserInfoByName(userName);
        if (listOfUserInfo.size() != 3) {
            messageText = "Пользователь не найден";
        } else {
            UUID userId = UUID.fromString(listOfUserInfo.get(0));
            Set<RecommendationForUserDTO> recommendations = recommendationService.recommend(userId);
            StringBuilder newProducts = new StringBuilder();
            if (recommendations.isEmpty()) {
                newProducts.append("Новых продуктов нет");
            } else {
                for (RecommendationForUserDTO recommendation : recommendations) {
                    newProducts.append(formatRecommendation(recommendation));
                }
            }
            messageText = "Здравствуйте, " + listOfUserInfo.get(1) +
                    " " + listOfUserInfo.get(2) +
                    "\n\nНовые продукты для вас:\n" + newProducts;
        }
        return new SendMessage(message.chat().id(), messageText);
    }

    private String formatRecommendation(RecommendationForUserDTO recommendation) {
        return "\n" +
                recommendation.getName() +
                "\n" +
                recommendation.getText() +
                "\n";
    }
}
