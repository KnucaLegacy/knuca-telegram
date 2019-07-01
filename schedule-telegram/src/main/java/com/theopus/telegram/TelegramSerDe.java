package com.theopus.telegram;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TelegramSerDe {

    private final ObjectMapper mapper;

    public TelegramSerDe(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    public String serialize(Object object) {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String serializeForCommand(String command, Object object) {
        try {
            return "/" + command + " " + mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T deserialize(String string, Class<T> type){
        try {
            return mapper.readValue(string, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
