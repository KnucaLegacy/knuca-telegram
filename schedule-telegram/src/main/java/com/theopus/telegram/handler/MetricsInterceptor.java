package com.theopus.telegram.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theopus.telegram.bot.TelegramHandlerInterceptor;
import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;
import com.theopus.telegram.metric.MetricService;

public class MetricsInterceptor implements TelegramHandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricsInterceptor.class);

    private final MetricService metricService;

    public MetricsInterceptor(MetricService metricService) {
        this.metricService = metricService;
    }

    @Override
    public boolean preHandle(TelegramRequest request) {
        metricService.trackAsync(request);
        return true;
    }

    @Override
    public void postHandle(TelegramRequest request, TelegramResponse response) {

    }

}
