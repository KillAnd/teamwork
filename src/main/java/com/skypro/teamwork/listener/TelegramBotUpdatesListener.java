package com.skypro.teamwork.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.skypro.teamwork.service.MessageSender;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);
    private final String WELCOME_MESSAGE = "Приветствуем вас в нашем банке!";
    private final TelegramBot telegramBot;
    private final MessageSender messageSender;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, MessageSender messageSender) {
        this.telegramBot = telegramBot;
        this.messageSender = messageSender;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            Long chatId = update.message().chat().id();
            String messageText = update.message().text();

            if ("/start".equals(messageText)) {
                messageSender.send(chatId, WELCOME_MESSAGE);
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}