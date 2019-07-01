package com.theopus.telegram.bot.handlers;

import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;

public interface TelegramHandler {
    String COMMAND_CHARACTER = "/";

    TelegramResponse handle(TelegramRequest req);

    /**
     * @return command for dispatching or null to be default handler. (Default handler can be only one)
     */
    default String command(){
        return "";
    }

}
