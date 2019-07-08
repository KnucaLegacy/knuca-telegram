package com.theopus.telegram.backend.repository;

import java.util.stream.Stream;

import com.theopus.telegram.backend.entity.User;

public interface UserRepository {
    User get(Integer id);

    Stream<User> all();

    User add(User user);
}
