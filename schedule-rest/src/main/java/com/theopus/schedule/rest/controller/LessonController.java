package com.theopus.schedule.rest.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.LessonRepository;
import com.theopus.schedule.backend.repository.Repository;

@RestController
@RequestMapping("/lessons")
public class LessonController {

    private LessonRepository service;
    private Repository<Group> groupService;
    private Repository<Teacher> teacherService;
    private Repository<Room> roomService;

    @Autowired
    public LessonController(LessonRepository service, Repository<Group> groupService, Repository<Teacher> teacherService, Repository<Room> roomService) {
        this.service = service;
        this.groupService = groupService;
        this.teacherService = teacherService;
        this.roomService = roomService;
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<Lesson>> byGroup(@PathVariable Long id) {
        return ResponseEntity.ok(service.byGroup(group(id), LocalDate.now()));
    }

    @GetMapping("/{yyyy-MM-dd}/group/{id}")
    public ResponseEntity<List<Lesson>> byGroupAndDate(@PathVariable Long id, @PathVariable("yyyy-MM-dd") LocalDate localDate) {
        return ResponseEntity.ok(service.byGroup(group(id), localDate));
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<Lesson>> byTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(service.byTeacher(teacher(id), LocalDate.now()));
    }

    @GetMapping("/{yyyy-MM-dd}/teacher/{id}")
    public ResponseEntity<List<Lesson>> byTeacherAndDate(@PathVariable Long id, @PathVariable("yyyy-MM-dd") LocalDate localDate) {
        return ResponseEntity.ok(service.byTeacher(teacher(id), localDate));
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<Lesson>> byRoom(@PathVariable Long id) {
        return ResponseEntity.ok(service.byRoom(room(id), LocalDate.now()));
    }

    @GetMapping("/{yyyy-MM-dd}/room/{id}")
    public ResponseEntity<List<Lesson>> byRoomAndDate(@PathVariable Long id, @PathVariable("yyyy-MM-dd") LocalDate localDate) {
        return ResponseEntity.ok(service.byRoom(room(id), localDate));
    }

    @GetMapping("/week/room/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byRoomAndWeek(@PathVariable Long id) {
        ImmutablePair<LocalDate, LocalDate> week = weekFromTo(LocalDate.now());
        return ResponseEntity.ok(service.byRoom(room(id), week.left, week.right));
    }

    @GetMapping("/week/group/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byGroupAndWeek(@PathVariable Long id) {
        ImmutablePair<LocalDate, LocalDate> week = weekFromTo(LocalDate.now());
        Map<LocalDate, List<Lesson>> body = service.byGroup(group(id), week.left, week.right);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/week/teacher/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byTeacherAndWeek(@PathVariable Long id) {
        ImmutablePair<LocalDate, LocalDate> week = weekFromTo(LocalDate.now());
        return ResponseEntity.ok(service.byTeacher(teacher(id), week.left, week.right));
    }

    @GetMapping("/week/{offset}/room/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byRoomAndWeekOffset(@PathVariable Integer offset,
                                                                            @PathVariable Long id) {
        ImmutablePair<LocalDate, LocalDate> week = weekFromTo(LocalDate.now(), offset);
        return ResponseEntity.ok(service.byRoom(room(id), week.left, week.right));
    }

    @GetMapping("/week/{offset}/group/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byGroupAndDateOffset(@PathVariable Integer offset,
                                                                             @PathVariable Long id) {
        ImmutablePair<LocalDate, LocalDate> week = weekFromTo(LocalDate.now(), offset);
        return ResponseEntity.ok(service.byGroup(group(id), week.left, week.right));
    }

    @GetMapping("/week/{offset}/teacher/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byTeacherAndWeekOffset(@PathVariable Integer offset,
                                                                               @PathVariable Long id) {
        ImmutablePair<LocalDate, LocalDate> week = weekFromTo(LocalDate.now(), offset);
        return ResponseEntity.ok(service.byTeacher(teacher(id), week.left, week.right));
    }

    @GetMapping("/range/room/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byRoomAndRange(
            @RequestParam(name = "from", required = true) LocalDate from,
            @RequestParam(name = "fo", required = true) LocalDate to,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.byRoom(room(id), from, to));
    }

    @GetMapping("/range/group/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byGroupAndRange(
            @RequestParam(name = "from", required = true) LocalDate from,
            @RequestParam(name = "fo", required = true) LocalDate to,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.byGroup(group(id), from, to));
    }

    @GetMapping("/range/teacher/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byTeacherAndRange(
            @RequestParam(name = "from", required = true) LocalDate from,
            @RequestParam(name = "fo", required = true) LocalDate to,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.byTeacher(teacher(id), from, to));
    }

    private Room room(Long id) {
        Room room = roomService.get(id);
        if (Objects.isNull(room)) {
            throw new EntityNotFoundException("Not found Room with id " + id);
        }
        return room;
    }

    private Teacher teacher(Long id) {
        Teacher teacher = teacherService.get(id);
        if (Objects.isNull(teacher)) {
            throw new EntityNotFoundException("Not found Teacher with id " + id);
        }
        return teacher;
    }

    private Group group(Long id) {
        Group group = groupService.get(id);
        if (Objects.isNull(group)) {
            throw new EntityNotFoundException("Not found Group with id " + id);
        }
        return group;
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


    private static Set<LocalDate> range(LocalDate from, LocalDate to) {
        final int days = (int) from.until(to, ChronoUnit.DAYS);
        return Stream.iterate(from, d -> d.plusDays(1))
                .limit(days).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
