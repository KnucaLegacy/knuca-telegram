package com.theopus.telegram.backend.repository;

import java.util.stream.Stream;

import com.theopus.telegram.backend.entity.Chat;

public interface ChatRepository {

    Chat get(Long id);

    Stream<Chat> all();

    Chat add(Chat chat);

    Chat update(Chat chat);
}
