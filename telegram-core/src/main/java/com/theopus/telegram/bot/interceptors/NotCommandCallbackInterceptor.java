package com.theopus.telegram.bot.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theopus.telegram.bot.TelegramHandlerInterceptor;
import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;

public class NotCommandCallbackInterceptor implements TelegramHandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(NotCommandCallbackInterceptor.class);

    @Override
    public boolean preHandle(TelegramRequest request) {
        if (request.isCallback()) {
            LOGGER.warn("Filter not command callback {}", request);
            return request.isCommand();
        }
        return true;
    }

    @Override
    public void postHandle(TelegramRequest request, TelegramResponse response) {

    }
}
