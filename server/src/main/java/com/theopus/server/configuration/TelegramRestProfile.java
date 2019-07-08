package com.theopus.server.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.theopus.schedule.rest.configuration.ScheduleRestConfiguration;
import com.theopus.telegram.rest.configuration.TelegramRestConfiguration;

@Configuration
@EnableWebMvc
@Import({TelegramRestConfiguration.class})
@Profile("telegram-rest")
public class TelegramRestProfile {

}
