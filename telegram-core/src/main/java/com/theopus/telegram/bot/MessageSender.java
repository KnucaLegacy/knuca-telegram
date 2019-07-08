package com.theopus.telegram.bot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.google.common.util.concurrent.RateLimiter;

public class MessageSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageSender.class);

    private final RateLimiter limiter;
    private final LinkedBlockingQueue<Runnable> queue;
    private final ExecutorService executor;

    public MessageSender(int rate) {
        limiter = RateLimiter.create(rate);
        queue = new LinkedBlockingQueue<>();
        executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                limiter.acquire();
                try {
                    queue.take().run();
                } catch (InterruptedException e) {
                    LOGGER.error("{}", e);
                }
            }
        });
    }

    public void sendAsync(Bot bot, BotApiMethod<?> msg) {
        queue.offer(() -> {
            try {
                bot.execute(msg);
            } catch (TelegramApiException e) {
                LOGGER.error("Error sending message {}", e);
//                if (!Thread.currentThread().isInterrupted()) {
//                    sendAsync(bot, msg);
//                }
            }
        });
    }


    public void close() throws InterruptedException {
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
    }
}
