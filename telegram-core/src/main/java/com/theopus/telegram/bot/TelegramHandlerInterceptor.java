package com.theopus.telegram.bot;

public interface TelegramHandlerInterceptor {

    void doBefore(TelegramRequest request);
    void doAfter(TelegramRequest request, TelegramResponse response);
}
