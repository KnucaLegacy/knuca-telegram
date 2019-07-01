package com.theopus.schedule.backend.repository;

import java.util.List;

public interface Repository<T> {
    List<T> all();

    T get (Long id);
}
