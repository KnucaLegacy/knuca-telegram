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
import com.theopus.telegram.bot.interceptors.LoggingInterceptor;

public class TelegramDispatcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramDispatcher.class);
    private static final Pattern commandPattern = Pattern.compile("(^|\\s)/([^/\\s]+)(\\s)?");
    private static final Pattern mentionPattern = Pattern.compile("(^|\\s)?@([^/\\s,;]+)(\\s)?");

    private final Map<String, TelegramHandler> handlers;
    private final List<TelegramHandlerInterceptor> interceptors;
    private final TelegramSender sender;

    public TelegramDispatcher() {
        handlers = new HashMap<>();
        interceptors = new ArrayList<>();
        sender = new TelegramSender();
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

        interceptors.forEach(ic -> ic.doBefore(req));
        TelegramResponse res = handler.handle(req);
        interceptors.forEach(ic -> ic.doAfter(req, res));

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
        if (commandMatcher.find()) {
            commandStart = commandMatcher.start();
            req.setCommand(commandMatcher.group(2))
                    .setData(msg.substring(commandMatcher.end()).trim());
        } else {
            commandStart = msg.length() - 1;
            req.setData(msg);
        }

        Matcher mentionMatcher = mentionPattern.matcher(msg.substring(0, commandStart));
        while (mentionMatcher.find()) {
            String mention = mentionMatcher.group(2);
            req.addMention(mention);
        }
    }

    public void register(LoggingInterceptor loggingInterceptor) {
        interceptors.add(loggingInterceptor);
    }
}