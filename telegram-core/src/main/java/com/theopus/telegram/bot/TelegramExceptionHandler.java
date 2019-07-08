package com.theopus.telegram.bot;

import com.theopus.telegram.bot.handlers.TelegramHandler;

public interface TelegramExceptionHandler {

    void handle(TelegramHandler handler, TelegramRequest request, Exception e);
}
