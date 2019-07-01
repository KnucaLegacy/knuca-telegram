package com.theopus.telegram.handler;

import java.util.List;
import java.util.stream.Collectors;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.theopus.schedule.backend.search.Storage;
import com.theopus.telegram.TelegramSerDe;
import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;
import com.theopus.telegram.bot.handlers.TelegramHandler;
import com.theopus.telegram.handler.entity.ScheduleCommandData;

public class BaseSearchHandler implements TelegramHandler {

    private final Storage storage;
    private final TelegramSerDe serDe;

    public BaseSearchHandler(Storage storage, TelegramSerDe serDe) {
        this.storage = storage;
        this.serDe = serDe;
    }

    @Override
    public TelegramResponse handle(TelegramRequest req) {
        List<Storage.SearchResult> searchResult = storage.search(req.getData());
        if (searchResult.isEmpty()) {
            return TelegramResponse.body("Не знаю что такое " + req.getData());
        }

        return TelegramResponse.buttons(searchResult.stream()
                .map(s -> new InlineKeyboardButton()
                        .setText(s.name)
                        .setCallbackData(serDe.serializeForCommand(ScheduleHandler.COMMAND, new ScheduleCommandData(s))))
                .collect(Collectors.toList()));
    }

}
