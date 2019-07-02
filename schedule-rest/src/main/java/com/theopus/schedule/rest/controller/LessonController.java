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
import com.theopus.schedule.backend.repository.Repository;
import com.theopus.schedule.backend.service.LessonService;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private LessonService service;
    private Repository<Group> groupService;
    private Repository<Teacher> teacherService;
    private Repository<Room> roomService;

    @Autowired
    public LessonController(LessonService service, Repository<Group> groupService, Repository<Teacher> teacherService, Repository<Room> roomService) {
        this.service = service;
        this.groupService = groupService;
        this.teacherService = teacherService;
        this.roomService = roomService;
    }

    @GetMapping("/group/{id}")
    public ResponseEntity<List<Lesson>> byGroup(@PathVariable Long id) {
        return ResponseEntity.ok(check(service.at(id, LocalDate.now(), Group.class), id));
    }

    @GetMapping("/{yyyy-MM-dd}/group/{id}")
    public ResponseEntity<List<Lesson>> byGroupAndDate(@PathVariable Long id, @PathVariable("yyyy-MM-dd") LocalDate localDate) {
        return ResponseEntity.ok(check(service.at(id, localDate, Group.class), id));
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<List<Lesson>> byTeacher(@PathVariable Long id) {
        return ResponseEntity.ok(check(service.at(id, LocalDate.now(), Teacher.class), id));
    }

    @GetMapping("/{yyyy-MM-dd}/teacher/{id}")
    public ResponseEntity<List<Lesson>> byTeacherAndDate(@PathVariable Long id, @PathVariable("yyyy-MM-dd") LocalDate localDate) {
        return ResponseEntity.ok(check(service.at(id, localDate, Teacher.class), id));
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<Lesson>> byRoom(@PathVariable Long id) {
        return ResponseEntity.ok(check(service.at(id, LocalDate.now(), Room.class), id));
    }

    @GetMapping("/{yyyy-MM-dd}/room/{id}")
    public ResponseEntity<List<Lesson>> byRoomAndDate(@PathVariable Long id, @PathVariable("yyyy-MM-dd") LocalDate localDate) {
        return ResponseEntity.ok(check(service.at(id, localDate, Room.class), id));
    }

    @GetMapping("/week/room/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byRoomAndWeek(@PathVariable Long id) {
        return ResponseEntity.ok(check(service.week(id, Room.class), id));
    }

    @GetMapping("/week/group/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byGroupAndWeek(@PathVariable Long id) {
        Map<LocalDate, List<Lesson>> body = check(service.week(id, Group.class), id);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/week/teacher/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byTeacherAndWeek(@PathVariable Long id) {
        return ResponseEntity.ok(check(service.week(id, Group.class), id));
    }

    @GetMapping("/week/{offset}/room/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byRoomAndWeekOffset(@PathVariable Integer offset,
                                                                            @PathVariable Long id) {
        return ResponseEntity.ok(check(service.week(id, offset, Room.class), id));
    }

    @GetMapping("/week/{offset}/group/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byGroupAndDateOffset(@PathVariable Integer offset,
                                                                             @PathVariable Long id) {
        return ResponseEntity.ok(check(service.week(id, offset, Group.class), id));
    }

    @GetMapping("/week/{offset}/teacher/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byTeacherAndWeekOffset(@PathVariable Integer offset,
                                                                               @PathVariable Long id) {
        return ResponseEntity.ok(check(service.week(id, offset, Teacher.class), id));
    }

    @GetMapping("/range/room/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byRoomAndRange(
            @RequestParam(name = "from", required = true) LocalDate from,
            @RequestParam(name = "fo", required = true) LocalDate to,
            @PathVariable Long id) {
        return ResponseEntity.ok(check(service.range(id, from, to, Room.class), id));
    }

    @GetMapping("/range/group/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byGroupAndRange(
            @RequestParam(name = "from", required = true) LocalDate from,
            @RequestParam(name = "fo", required = true) LocalDate to,
            @PathVariable Long id) {
        return ResponseEntity.ok(check(service.range(id, from, to, Group.class), id));
    }

    @GetMapping("/range/teacher/{id}")
    public ResponseEntity<Map<LocalDate, List<Lesson>>> byTeacherAndRange(
            @RequestParam(name = "from", required = true) LocalDate from,
            @RequestParam(name = "fo", required = true) LocalDate to,
            @PathVariable Long id) {
        return ResponseEntity.ok(check(service.range(id, from, to, Room.class), id));
    }

    private <T, U> U check(ImmutablePair<T, U> pair, Long id) {
        if (Objects.isNull(pair.left)) {
            throw new EntityNotFoundException("Not found Room with id " + id);
        } else {
            return pair.right;
        }
    }
}
