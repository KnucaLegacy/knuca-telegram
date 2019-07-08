package com.theopus.telegram.handler;

import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.theopus.telegram.FormatManager;
import com.theopus.telegram.TelegramSerDe;
import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;
import com.theopus.telegram.bot.handlers.TelegramHandler;
import com.theopus.telegram.handler.entity.ScheduleCommandData;
import com.theopus.telegram.handler.entity.SearchCommand;
import com.theopus.telegram.handler.entity.Type;

public class SearchHandler implements TelegramHandler {

    public static final String COMMAND = "search";


    private final TelegramSerDe serDe;
    private final FormatManager formatManager;

    public SearchHandler(TelegramSerDe serDe,
                         FormatManager formatManager,
                         SearchCommandConfigurer configurer) {
        this.serDe = serDe;
        this.formatManager = formatManager;

        this.menuActions = configurer
                .searchActions(
                        (command, groupStream) -> groupStream
                                .map(group -> ImmutablePair.of(group.getName(), new ScheduleCommandData(group.getId(),
                                        Type.GROUP,
                                        ScheduleCommandData.Action.TODAY))),
                        (command, teacherStream) -> teacherStream
                                .map(teacher -> ImmutablePair.of(teacher.getName(), new ScheduleCommandData(teacher.getId(),
                                        Type.TEACHER,
                                        ScheduleCommandData.Action.TODAY))),
                        (command, roomStream) -> roomStream
                                .map(room -> ImmutablePair.of(room.getName(), new ScheduleCommandData(room.getId(),
                                        Type.ROOM,
                                        ScheduleCommandData.Action.TODAY))),
                        ScheduleCommandData.class,
                        "[Поиск]",
                        ScheduleHandler.COMMAND);
    }

    public Set<MenuAction<SearchCommand, ?, ?>> menuActions;

    @Override
    public TelegramResponse handle(TelegramRequest req) {
        SearchCommand sc;
        if (req.getData().isEmpty()) {
            sc = new SearchCommand();
        } else {
            sc = serDe.deserialize(req.getData(), SearchCommand.class);
        }

        for (MenuAction<SearchCommand, ?, ?> menuAction : menuActions) {
            if (menuAction.predicate().test(sc)) {
                return menuAction.process(sc, req, serDe, formatManager);
            }
        }
        return TelegramResponse.body("");
    }

    @Override
    public String command() {
        return COMMAND;
    }

}
