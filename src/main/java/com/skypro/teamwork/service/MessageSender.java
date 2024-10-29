package com.skypro.teamwork.service;

public interface MessageSender {

    void send(Long chatId, String messageText);

}
