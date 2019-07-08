package com.theopus.telegram.metric;

import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;
import com.theopus.telegram.bot.handlers.TelegramHandler;

public interface MetricService {

    void track(TelegramRequest request);

    void track(TelegramRequest request, TelegramResponse response);

    void trackAsync(TelegramRequest request);

    void trackAsync(TelegramRequest request, TelegramResponse response);

    void trackException(TelegramRequest request, TelegramHandler handler, Exception e);

    void trackExceptionAsync(TelegramRequest request, TelegramHandler handler, Exception e);

    void close() throws InterruptedException;

}
