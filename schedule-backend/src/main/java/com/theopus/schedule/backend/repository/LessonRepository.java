package com.theopus.schedule.backend.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;

public interface LessonRepository {
    Map<LocalDate, List<Lesson>> byGroup(Group group, LocalDate from, LocalDate to);

    Map<LocalDate, List<Lesson>> byTeacher(Teacher teacher, LocalDate from, LocalDate to);

    Map<LocalDate, List<Lesson>> byRoom(Room room, LocalDate from, LocalDate to);

    List<Lesson> byGroup(Group group, LocalDate date);

    List<Lesson> byTeacher(Teacher teacher, LocalDate date);

    List<Lesson> byRoom(Room room, LocalDate date);
}
