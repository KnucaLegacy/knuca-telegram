package com.theopus.telegram.handler;

import com.theopus.telegram.FormatManager;
import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;
import com.theopus.telegram.bot.handlers.TelegramHandler;

public class HelloHandler implements TelegramHandler {
    private FormatManager fm;

    public HelloHandler(FormatManager fm) {
        this.fm = fm;
    }

    @Override
    public TelegramResponse handle(TelegramRequest req) {
        return TelegramResponse.body(fm.help()).setMarkup(true);
    }

    @Override
    public String command() {
        return "hello";
    }
}
