package com.theopus.server.configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.theopus.schedule.backend.search.StorageUpdater;
import com.theopus.telegram.configuration.BotConfiguration;
import com.theopus.telegram.configuration.TelegramBotAutoConfiguration;

@Configuration
@Import({BotConfiguration.class, TelegramBotAutoConfiguration.class})
@Profile("telegram-schedule")
public class ScheduleTelegramProfile {

    @Bean(destroyMethod = "shutdownNow")
    public ExecutorService scheduler(StorageUpdater updater) {
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(updater::process, 15, 15, TimeUnit.MINUTES);
        return service;
    }
}
