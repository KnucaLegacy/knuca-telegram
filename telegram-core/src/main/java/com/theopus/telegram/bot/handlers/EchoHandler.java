package com.theopus.telegram.bot.handlers;

import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;

public class EchoHandler implements TelegramHandler {
    @Override
    public TelegramResponse handle(TelegramRequest req) {
        return TelegramResponse.body(req.getData());
    }

    @Override
    public String command() {
        return "echo";
    }
}
