package com.theopus.telegram.rest.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.theopus.telegram.backend.configuration.TelegramBackendConfig;

@Configuration
@Import(TelegramBackendConfig.class)
@ComponentScan("com.theopus.telegram.rest.controller")
public class TelegramRestConfiguration {

}
