package com.theopus.telegram.backend.repository;

import java.io.DataOutput;
import java.io.IOException;
import java.util.Comparator;

import org.jetbrains.annotations.NotNull;
import org.mapdb.DataInput2;
import org.mapdb.DataOutput2;
import org.mapdb.serializer.GroupSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.theopus.telegram.backend.entity.Chat;

public class JacksonGroupSerializer<T> implements GroupSerializer<T> {
    private final ObjectMapper mapper;
    private final Class<T> type;

    public JacksonGroupSerializer(ObjectMapper mapper, Class<T> type) {
        this.mapper = mapper;
        this.type = type;
    }

    @Override
    public void serialize(@NotNull DataOutput2 out, @NotNull T value) throws IOException {
        System.out.println(mapper.writeValueAsString(value));
        mapper.writeValue((DataOutput) out, value);
    }

    @Override
    public T deserialize(@NotNull DataInput2 input, int available) throws IOException {
        return mapper.readValue(input, type);
    }

    @Override
    public int valueArraySearch(Object keys, T key) {
        return 0;
    }

    @Override
    public int valueArraySearch(Object keys, T key, Comparator comparator) {
        return 0;
    }

    @Override
    public void valueArraySerialize(DataOutput2 out, Object vals) throws IOException {

    }

    @Override
    public Object valueArrayDeserialize(DataInput2 in, int size) throws IOException {
        return null;
    }

    @Override
    public T valueArrayGet(Object vals, int pos) {
        return null;
    }

    @Override
    public int valueArraySize(Object vals) {
        return 0;
    }

    @Override
    public Object valueArrayEmpty() {
        return null;
    }

    @Override
    public Object valueArrayPut(Object vals, int pos, T newValue) {
        return null;
    }

    @Override
    public Object valueArrayUpdateVal(Object vals, int pos, T newValue) {
        return null;
    }

    @Override
    public Object valueArrayFromArray(Object[] objects) {
        return null;
    }

    @Override
    public Object valueArrayCopyOfRange(Object vals, int from, int to) {
        return null;
    }

    @Override
    public Object valueArrayDeleteValue(Object vals, int pos) {
        return null;
    }
}
