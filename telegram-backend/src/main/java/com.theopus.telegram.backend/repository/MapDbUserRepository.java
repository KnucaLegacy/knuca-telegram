package com.theopus.telegram.backend.repository;

import java.util.Map;
import java.util.stream.Stream;

import org.mapdb.DB;
import org.mapdb.Serializer;
import org.mapdb.serializer.SerializerCompressionWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theopus.telegram.backend.entity.User;

public class MapDbUserRepository implements UserRepository {

    private final DB db;
    private Map<Integer, User> storage;

    public MapDbUserRepository(DB db, ObjectMapper mapper) {
        this.db = db;
        this.storage = db.hashMap("user_storage")
                .keySerializer(Serializer.INTEGER)
                .valueSerializer(new SerializerCompressionWrapper<>(new JacksonGroupSerializer<>(mapper, User.class)))
                .createOrOpen();
    }

    @Override
    public User get(Integer id) {
        return storage.getOrDefault(id, null);
    }

    @Override
    public Stream<User> all() {
        return storage.values().stream();
    }

    @Override
    public User add(User user) {
        User put = storage.put(user.getId(), user);
        db.commit();
        return put;
    }

    public User update(User user) {
        return add(user);
    }
}
