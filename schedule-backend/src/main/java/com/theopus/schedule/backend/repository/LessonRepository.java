package com.theopus.schedule.backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.theopus.entity.schedule.Lesson;

public interface LessonRepository<T> {
    Map<LocalDate, List<Lesson>> range(T t, LocalDate from, LocalDate to);

    List<Lesson> at(T t, LocalDate date);
}
