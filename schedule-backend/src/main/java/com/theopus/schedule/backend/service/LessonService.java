package com.theopus.schedule.backend.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.ImmutableMap;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.LessonRepository;
import com.theopus.schedule.backend.repository.Repository;

public class LessonService {

    private Map<Class<?>, Repository<?>> repos;
    private Map<Class<?>, LessonRepository<?>> lessonRepos;

    public LessonService(Repository<Room> roomRepo, Repository<Group> groupRepo, Repository<Teacher> teacherRepo,
                         LessonRepository<Room> roomLessonRepo, LessonRepository<Group> groupLessonRepo, LessonRepository<Teacher> teacherLessonRepo) {
        this.repos = ImmutableMap.of(
                Group.class, groupRepo,
                Teacher.class, teacherRepo,
                Room.class, roomRepo);
        this.lessonRepos = ImmutableMap.of(
                Group.class, groupLessonRepo,
                Teacher.class, teacherLessonRepo,
                Room.class, roomLessonRepo);
    }

    public <T> ImmutablePair<T, Map<LocalDate, List<Lesson>>> range(Long id, LocalDate from, LocalDate to, Class<T> type) {
        T t = getRepo(type).get(id);
        if (t == null) {
            return ImmutablePair.of(null, null);
        }
        return ImmutablePair.of(t, getLessonRepo(type).range(t, from, to));
    }

    public <T> ImmutablePair<T, List<Lesson>> at(Long id, LocalDate date, Class<T> type) {
        T group = getRepo(type).get(id);
        if (group == null) {
            return ImmutablePair.of(null, null);
        }
        return ImmutablePair.of(group, getLessonRepo(type).at(group, date));
    }

    public <T> ImmutablePair<T, Map<LocalDate, List<Lesson>>> week(Long id, LocalDate date, Class<T> type) {
        T group = getRepo(type).get(id);
        if (group == null) {
            return ImmutablePair.of(null, null);
        }
        ImmutablePair<LocalDate, LocalDate> pair = weekFromTo(date);
        return ImmutablePair.of(group, getLessonRepo(type).range(group, pair.left, pair.right));
    }

    public <T> ImmutablePair<T, Map<LocalDate, List<Lesson>>> week(Long id, int offset, Class<T> type) {
        T group = getRepo(type).get(id);
        if (group == null) {
            return ImmutablePair.of(null, null);
        }
        ImmutablePair<LocalDate, LocalDate> pair = weekFromTo(LocalDate.now(), offset);
        return ImmutablePair.of(group, getLessonRepo(type).range(group, pair.left, pair.right));
    }

    public <T>ImmutablePair<T, Map<LocalDate, List<Lesson>>> week(Long id, Class<T> type) {
        return week(id, LocalDate.now(), type);
    }

    private <T> Repository<T> getRepo(Class<T> type) {
        return (Repository<T>) repos.get(type);
    }

    private <T> LessonRepository<T> getLessonRepo(Class<T> type) {
        return (LessonRepository<T>) lessonRepos.get(type);
    }

    private static ImmutablePair<LocalDate, LocalDate> weekFromTo(LocalDate localDate) {
        return weekFromTo(localDate, 0);
    }

    private static ImmutablePair<LocalDate, LocalDate> weekFromTo(LocalDate localDate, int offset) {
        LocalDate newDate = localDate.plusDays(7 * offset);
        return ImmutablePair.of(
                newDate.with(DayOfWeek.MONDAY),
                newDate.with(DayOfWeek.SUNDAY)
        );
    }
}
