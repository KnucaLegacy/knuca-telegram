package com.theopus.schedule.backend.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.LessonRepository;
import com.theopus.schedule.backend.repository.Repository;

public class LessonService {

    private LessonRepository lessonRepo;
    private Repository<Room> roomRepo;
    private Repository<Group> groupRepo;
    private Repository<Teacher> teacherRepo;

    public LessonService(LessonRepository lessonRepo, Repository<Room> roomRepo, Repository<Group> groupRepo, Repository<Teacher> teacherRepo) {
        this.lessonRepo = lessonRepo;
        this.roomRepo = roomRepo;
        this.groupRepo = groupRepo;
        this.teacherRepo = teacherRepo;
    }

    public ImmutablePair<Group, Map<LocalDate, List<Lesson>>> withGroup(Long id, LocalDate from, LocalDate to) {
        Group group = groupRepo.get(id);
        if (group == null) {
            return ImmutablePair.of(null, null);
        }
        return ImmutablePair.of(group, lessonRepo.byGroup(group, from, to));
    }

    public ImmutablePair<Group, List<Lesson>> withGroup(Long id, LocalDate date) {
        Group group = groupRepo.get(id);
        if (group == null) {
            return ImmutablePair.of(null, null);
        }
        return ImmutablePair.of(group, lessonRepo.byGroup(group, date));
    }

    public ImmutablePair<Group, Map<LocalDate, List<Lesson>>> withGroupWeek(Long id, LocalDate date) {
        Group group = groupRepo.get(id);
        if (group == null) {
            return ImmutablePair.of(null, null);
        }
        ImmutablePair<LocalDate, LocalDate> pair = weekFromTo(date);
        return ImmutablePair.of(group, lessonRepo.byGroup(group, pair.left, pair.right));
    }

    public ImmutablePair<Group, Map<LocalDate, List<Lesson>>> withGroupWeek(Long id, int offset) {
        Group group = groupRepo.get(id);
        if (group == null) {
            return ImmutablePair.of(null, null);
        }
        ImmutablePair<LocalDate, LocalDate> pair = weekFromTo(LocalDate.now(), offset);
        return ImmutablePair.of(group, lessonRepo.byGroup(group, pair.left, pair.right));
    }

    public ImmutablePair<Group, Map<LocalDate, List<Lesson>>> withGroupWeek(Long id) {
        return withGroupWeek(id, LocalDate.now());
    }

    public ImmutablePair<Teacher, Map<LocalDate, List<Lesson>>> withTeacher(Long id, LocalDate from, LocalDate to) {
        Teacher teacher = teacherRepo.get(id);
        if (teacher == null) {
            return ImmutablePair.of(null, null);
        }
        return ImmutablePair.of(teacher, lessonRepo.byTeacher(teacher, from, to));
    }

    public ImmutablePair<Teacher, List<Lesson>> withTeacher(Long id, LocalDate date) {
        Teacher teacher = teacherRepo.get(id);
        if (teacher == null) {
            return ImmutablePair.of(null, null);
        }
        return ImmutablePair.of(teacher, lessonRepo.byTeacher(teacher, date));
    }

    public ImmutablePair<Teacher, Map<LocalDate, List<Lesson>>> withTeacherWeek(Long id, LocalDate date) {
        Teacher teacher = teacherRepo.get(id);
        if (teacher == null) {
            return ImmutablePair.of(null, null);
        }
        ImmutablePair<LocalDate, LocalDate> pair = weekFromTo(date);
        return ImmutablePair.of(teacher, lessonRepo.byTeacher(teacher, pair.left, pair.right));
    }

    public ImmutablePair<Teacher, Map<LocalDate, List<Lesson>>> withTeacherWeek(Long id, int offset) {
        Teacher teacher = teacherRepo.get(id);
        if (teacher == null) {
            return ImmutablePair.of(null, null);
        }
        ImmutablePair<LocalDate, LocalDate> pair = weekFromTo(LocalDate.now(), offset);
        return ImmutablePair.of(teacher, lessonRepo.byTeacher(teacher, pair.left, pair.right));
    }

    public ImmutablePair<Teacher, Map<LocalDate, List<Lesson>>> withTeacherWeek(Long id) {
        return withTeacherWeek(id, LocalDate.now());
    }

    public ImmutablePair<Room, Map<LocalDate, List<Lesson>>> withRoom(Long id, LocalDate from, LocalDate to) {
        Room room = roomRepo.get(id);
        if (room == null) {
            return ImmutablePair.of(null, null);
        }
        return ImmutablePair.of(room, lessonRepo.byRoom(room, from, to));
    }

    public ImmutablePair<Room, List<Lesson>> withRoom(Long id, LocalDate date) {
        Room room = roomRepo.get(id);
        if (room == null) {
            return ImmutablePair.of(null, null);
        }
        return ImmutablePair.of(room, lessonRepo.byRoom(room, date));
    }

    public ImmutablePair<Room, Map<LocalDate, List<Lesson>>> withRoomWeek(Long id, LocalDate date) {
        Room room = roomRepo.get(id);
        if (room == null) {
            return ImmutablePair.of(null, null);
        }
        ImmutablePair<LocalDate, LocalDate> pair = weekFromTo(date);
        return ImmutablePair.of(room, lessonRepo.byRoom(room, pair.left, pair.right));
    }

    public ImmutablePair<Room, Map<LocalDate, List<Lesson>>> withRoomWeek(Long id, int offset) {
        Room room = roomRepo.get(id);
        if (room == null) {
            return ImmutablePair.of(null, null);
        }
        ImmutablePair<LocalDate, LocalDate> pair = weekFromTo(LocalDate.now(), offset);
        return ImmutablePair.of(room, lessonRepo.byRoom(room, pair.left, pair.right));
    }

    public ImmutablePair<Room, Map<LocalDate, List<Lesson>>> withRoomWeek(Long id) {
        return withRoomWeek(id, LocalDate.now());
    }

    private static ImmutablePair<LocalDate, LocalDate> weekFromTo() {
        return weekFromTo(LocalDate.now(), 0);
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
