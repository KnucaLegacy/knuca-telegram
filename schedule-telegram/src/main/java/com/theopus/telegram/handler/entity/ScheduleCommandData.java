package com.theopus.telegram.handler.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theopus.schedule.backend.search.Storage;

public class ScheduleCommandData {

    @JsonProperty("i")
    private Long id;
    @JsonProperty("t")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Type type;
    @JsonProperty("a")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Action action;

    public ScheduleCommandData(Long id, Type type, Action action) {
        this.id = id;
        this.type = type;
        this.action = action;
    }

    public ScheduleCommandData(Storage.SearchResult searchResult) {
        this.id = searchResult.id;
        this.type = Type.valueOf(searchResult.type.name());
        this.action = Action.TODAY;
    }

    @JsonCreator
    public ScheduleCommandData(@JsonProperty("i") Long id, @JsonProperty("t") int type, @JsonProperty("a ") int action) {
        this.id = id;
        this.type = Type.values()[type];
        this.action = Action.values()[action];
    }

    public enum Action {
        TODAY,
        TOMORROW,
        WEEK,
        NEXT_WEEK
    }

    public Long getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public Action getAction() {
        return action;
    }
}
