package com.theopus.telegram.bot.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theopus.telegram.bot.TelegramHandlerInterceptor;
import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;

public class LoggingInterceptor implements TelegramHandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public void doBefore(TelegramRequest request) {
        LOGGER.info("Msg: {}, Chat: {}, From: {}", request.getOriginalMessage(), request.getChat().getId(), request.getFrom().getId());
    }

    @Override
    public void doAfter(TelegramRequest request, TelegramResponse response) {
        LOGGER.info("Response: {}, To Chat: {}, Was From: {}", response, request.getChat().getId(), request.getFrom().getId());
    }
}
