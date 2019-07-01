package com.theopus.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.theopus.telegram.configuration.BotConfiguration;
import com.theopus.telegram.configuration.TelegramBotAutoConfiguration;

@Configuration
@Import({BotConfiguration.class, TelegramBotAutoConfiguration.class})
@Profile("telegram-schedule")
public class ScheduleTelegramProfile {

}
