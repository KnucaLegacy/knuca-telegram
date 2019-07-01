package com.theopus.telegram.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    private static final Logger LOGGER = LoggerFactory.getLogger(Bot.class);

    private final String botUsername;
    private final String botToken;
    private TelegramDispatcher dispatcher;

    public Bot(String botUsername, String botToken, TelegramDispatcher dispatcher) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.dispatcher = dispatcher;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                User from = update.getMessage().getFrom();
                Chat chat = update.getMessage().getChat();
                String data = update.getMessage().getText();

                dispatcher.dispatch(this, new TelegramRequest(from, chat, data, update)
                        .setCallback(false));
            }

            if (update.hasCallbackQuery()) {
                User from = update.getCallbackQuery().getFrom();
                //message will be sent to chat to which is original mesasge from
                Chat chat = update.getCallbackQuery().getMessage().getChat();
                String data = update.getCallbackQuery().getData();

                dispatcher.dispatch(this, new TelegramRequest(from, chat, data, update)
                        .setCallback(true));
            }
        } catch (TelegramApiException e) {
            LOGGER.error("{}", e);
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }


}
