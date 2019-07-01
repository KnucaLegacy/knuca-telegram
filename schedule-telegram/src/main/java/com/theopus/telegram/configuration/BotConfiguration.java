package com.theopus.telegram.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theopus.schedule.backend.configuration.StorageConfiguration;
import com.theopus.schedule.backend.search.Storage;
import com.theopus.schedule.backend.search.StorageUpdater;
import com.theopus.schedule.backend.service.LessonService;
import com.theopus.telegram.FormatManager;
import com.theopus.telegram.TelegramSerDe;
import com.theopus.telegram.bot.Bot;
import com.theopus.telegram.bot.TelegramDispatcher;
import com.theopus.telegram.bot.handlers.EchoHandler;
import com.theopus.telegram.bot.interceptors.LoggingInterceptor;
import com.theopus.telegram.handler.BaseSearchHandler;
import com.theopus.telegram.handler.ScheduleHandler;

@Configuration
@Import(StorageConfiguration.class)
public class BotConfiguration {

    @Value("${telegram.bot.username}")
    private String username;
    @Value("${telegram.bot.token}")
    private String token;

    @Bean
    public ObjectMapper mapper() {
        return new ObjectMapper();
    }


    @Bean
    public TelegramSerDe serDe(ObjectMapper mapper) {
        return new TelegramSerDe(mapper);
    }

    @Bean
    public TelegramDispatcher dispatcher(Storage storage,
                                         TelegramSerDe telegramSerDe,
                                         LessonService service) {
        TelegramDispatcher telegramDispatcher = new TelegramDispatcher();
        FormatManager formatManager = new FormatManager();
        telegramDispatcher.register(new EchoHandler());
        telegramDispatcher.register(new BaseSearchHandler(storage, telegramSerDe, formatManager));
        telegramDispatcher.register(new LoggingInterceptor());
        telegramDispatcher.register(new ScheduleHandler(telegramSerDe, service, formatManager));
        return telegramDispatcher;
    }

    @Bean
    public ExecutorService scheduler(StorageUpdater updater) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(updater::process, 15, 15, TimeUnit.MINUTES);
        return service;
    }


    @Bean
    @DependsOn({"storageUpdater", "scheduler"})
    public Bot bot(TelegramDispatcher dispatcher) {
        Bot bot = new Bot(username, token, dispatcher);
        return bot;
    }
}
