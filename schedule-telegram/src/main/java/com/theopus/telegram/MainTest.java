package com.theopus.telegram;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.theopus.telegram.configuration.BotConfiguration;
import com.theopus.telegram.configuration.TelegramBotAutoConfiguration;


public class MainTest {
    public static void main(String[] args) {
        System.setProperty("telegram.bot.username", "KNUCA_InfoBot");
        System.setProperty("telegram.bot.token", "395659381:AAGf6YTIAEgwNYeTjrSRPPeSnUGEZLEU81s");
        ApplicationContext ctx = new AnnotationConfigApplicationContext(BotConfiguration.class, TelegramBotAutoConfiguration.class);
    }
}
