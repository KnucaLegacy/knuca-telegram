package com.theopus.telegram.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.theopus.telegram.bot.handlers.TelegramHandler;

public class TelegramDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramDispatcher.class);
    private static final Pattern commandPattern = Pattern.compile("(^|\\s)/([^/\\s]+)(\\s)?");
    private static final Pattern mentionPattern = Pattern.compile("(^|\\s)?@([^/\\s,;]+)(\\s)?");

    private final Map<String, TelegramHandler> handlers;
    private final List<TelegramHandlerInterceptor> interceptors;
    private final List<TelegramExceptionHandler> exceptionHandlers;
    private final TelegramSender sender;

    public TelegramDispatcher() {
        handlers = new HashMap<>();
        interceptors = new ArrayList<>();
        sender = new TelegramSender();
        exceptionHandlers = new ArrayList<>();
    }

    public void dispatch(Bot bot, TelegramRequest req) throws TelegramApiException {
        if (req.getOriginalMessage() == null || req.getOriginalMessage().isEmpty()) {
            return;
        }
        extractInfo(req);

        if (req.isCommand()) {
            handle(bot, req.getCommand(), req);
        }

        Chat chat = req.getChat();
        if (!req.isCommand() && chat.isUserChat()) {
            //default chat handler
            handle(bot, "default", req);
        }

        if (!req.isCommand()
                && (chat.isGroupChat() || chat.isSuperGroupChat())
                && req.hasMention(bot.getBotUsername())) {
            //default group chat handler, (mention required)
            handle(bot, "default", req);
        }
    }

    private void handle(Bot bot, String command, TelegramRequest req) throws TelegramApiException {
        TelegramHandler handler = handlers.get(command);
        if (handler == null) {
            LOGGER.error("Not found handler for [{}] command", command);
            return;
        }

        for (TelegramHandlerInterceptor interceptor : interceptors) {
            if (!interceptor.preHandle(req)) {
                LOGGER.warn("Broke chain by {}", interceptor);
                return;
            }
        }

        TelegramResponse res;
        try {
            res = handler.handle(req);
        } catch (Exception e) {
            exceptionHandlers.forEach(ex -> ex.handle(handler, req, e));
            throw e;
        }

        for (TelegramHandlerInterceptor interceptor : interceptors) {
            interceptor.postHandle(req, res);
        }

        sender.send(bot, req, res);
    }

    public void register(TelegramHandler handler) {
        if (handler.command() == null || handler.command().isEmpty()) {
            handlers.put("default", handler);
        } else {
            handlers.put(handler.command(), handler);
        }
    }

    public void extractInfo(TelegramRequest req) {
        String msg = req.getOriginalMessage();
        Matcher commandMatcher = commandPattern.matcher(msg);
        int commandStart;
        boolean containsCommand;
        if (commandMatcher.find()) {
            commandStart = commandMatcher.start();
            req.setCommand(commandMatcher.group(2))
                    .setData(msg.substring(commandMatcher.end()).trim());
            containsCommand = true;
        } else {
            commandStart = msg.length() - 1;
            req.setData(msg);
            containsCommand = false;
        }

        Matcher mentionMatcher = mentionPattern.matcher(msg.substring(0, commandStart));
        int mentionEnd = 0;
        while (mentionMatcher.find()) {
            String mention = mentionMatcher.group(2);
            req.addMention(mention);
            mentionEnd = mentionMatcher.end();
        }
        if (!containsCommand && mentionEnd != 0) {
            req.setData(req.getData().substring(mentionEnd).trim());
        }
    }

    public void register(TelegramHandlerInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void register(TelegramExceptionHandler handler) {
        exceptionHandlers.add(handler);
    }
}