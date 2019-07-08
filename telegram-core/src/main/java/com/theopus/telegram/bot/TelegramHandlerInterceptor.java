package com.theopus.telegram.bot;

public interface TelegramHandlerInterceptor {

    boolean preHandle(TelegramRequest request);

    void postHandle(TelegramRequest request, TelegramResponse response);

    default void afterCompleted(TelegramRequest request, TelegramResponse response) {

    }
}
