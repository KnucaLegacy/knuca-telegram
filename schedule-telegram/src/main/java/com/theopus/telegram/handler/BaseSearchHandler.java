package com.theopus.telegram.handler;

import java.util.List;
import java.util.stream.Collectors;

import com.theopus.schedule.backend.search.Storage;
import com.theopus.telegram.FormatManager;
import com.theopus.telegram.TelegramSerDe;
import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;
import com.theopus.telegram.bot.handlers.TelegramHandler;
import com.theopus.telegram.handler.entity.ScheduleCommandData;

public class BaseSearchHandler implements TelegramHandler {

    private final Storage storage;
    private final TelegramSerDe serDe;
    private final FormatManager manager;

    public BaseSearchHandler(Storage storage, TelegramSerDe serDe, FormatManager manager) {
        this.storage = storage;
        this.serDe = serDe;
        this.manager = manager;
    }

    @Override
    public TelegramResponse handle(TelegramRequest req) {
        List<Storage.SearchResult> searchResult = storage.search(req.getData());
        if (searchResult.isEmpty()) {
            return TelegramResponse.body("Не знаю что такое " + req.getData());
        }
        List<Storage.SearchResult> matchHit = searchResult.stream()
                .filter(s -> s.name.toLowerCase().equals(req.getData().toLowerCase()))
                .collect(Collectors.toList());
        if (matchHit.size() == 1) {
            searchResult = matchHit;
        }

        return TelegramResponse.buttons(searchResult.stream()
                .map(s -> ScheduleHandler.generator.generate(serDe, manager, new ScheduleCommandData(s), s.name))
                .collect(Collectors.toList()))
                .appendBold(req.getData() + ":");
    }

}
