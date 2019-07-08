package com.theopus.telegram.backend.repository;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;
import org.mapdb.DB;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.Serializer;
import org.mapdb.serializer.GroupSerializer;
import org.mapdb.serializer.SerializerCompressionWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theopus.telegram.backend.entity.Chat;

public class MapDbChatRepository implements ChatRepository {

    private final DB db;
    private Map<Long, Chat> storage;

    public MapDbChatRepository(DB db, ObjectMapper mapper) {
        this.db = db;
        this.storage = db.hashMap("chat_storage")
                .keySerializer(Serializer.LONG)
                .valueSerializer(new SerializerCompressionWrapper<>(new JacksonGroupSerializer<>(mapper, Chat.class)))
                .createOrOpen();
    }

    @Override
    public Chat get(Long id) {
        return storage.getOrDefault(id, null);
    }

    @Override
    public Stream<Chat> all() {
        return storage.values().stream();
    }

    @Override
    public Chat add(Chat chat) {
        Chat put = storage.put(chat.getId(), chat);
        db.commit();
        return put;
    }

    @Override
    public Chat update(Chat chat) {
        return add(chat);
    }

}
