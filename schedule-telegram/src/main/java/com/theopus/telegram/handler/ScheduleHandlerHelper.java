package com.theopus.telegram.handler;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.google.common.collect.ImmutableMap;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.service.LessonService;
import com.theopus.telegram.handler.entity.ScheduleCommandData;
import com.theopus.telegram.handler.entity.ScheduleCommandData.Action;
import com.theopus.telegram.handler.entity.Type;

public class ScheduleHandlerHelper {

    private static ImmutableMap<ImmutablePair<Action, Type>, ScheduleFunction> choices = new ImmutableMap.Builder()
            .put(p(Action.TODAY, Type.GROUP), (ScheduleFunction) (id, service) -> {
                LocalDate now = getNow();
                ImmutablePair<Group, List<Lesson>> response = service.at(id, now, Group.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), ImmutableMap.of(now, response.right));
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.TODAY, Type.TEACHER), (ScheduleFunction) (id, service) -> {
                LocalDate now = getNow();
                ImmutablePair<Teacher, List<Lesson>> response = service.at(id, now, Teacher.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), ImmutableMap.of(now, response.right));
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.TODAY, Type.ROOM), (ScheduleFunction) (id, service) -> {
                LocalDate now = getNow();
                ImmutablePair<Room, List<Lesson>> response = service.at(id, now, Room.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), ImmutableMap.of(now, response.right));
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.TOMORROW, Type.GROUP), (ScheduleFunction) (id, service) -> {
                LocalDate now = getNow().plusDays(1);
                ImmutablePair<Group, List<Lesson>> response = service.at(id, now, Group.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), ImmutableMap.of(now, response.right));
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.TOMORROW, Type.TEACHER), (ScheduleFunction) (id, service) -> {
                LocalDate now = getNow().plusDays(1);
                ;
                ImmutablePair<Teacher, List<Lesson>> response = service.at(id, now, Teacher.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), ImmutableMap.of(now, response.right));
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.TOMORROW, Type.ROOM), (ScheduleFunction) (id, service) -> {
                LocalDate now = getNow().plusDays(1);
                ;
                ImmutablePair<Room, List<Lesson>> response = service.at(id, now, Room.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), ImmutableMap.of(now, response.right));
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.WEEK, Type.GROUP), (ScheduleFunction) (id, service) -> {
                ImmutablePair<Group, Map<LocalDate, List<Lesson>>> response = service.week(id, getNow(), Group.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), response.right);
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.WEEK, Type.TEACHER), (ScheduleFunction) (id, service) -> {
                ImmutablePair<Teacher, Map<LocalDate, List<Lesson>>> response = service.week(id, getNow(), Teacher.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), response.right);
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.WEEK, Type.ROOM), (ScheduleFunction) (id, service) -> {
                ImmutablePair<Room, Map<LocalDate, List<Lesson>>> response = service.week(id, getNow(), Room.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), response.right);
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.NEXT_WEEK, Type.GROUP), (ScheduleFunction) (id, service) -> {
                ImmutablePair<Group, Map<LocalDate, List<Lesson>>> response = service.week(id, getNow().plusDays(7), Group.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), response.right);
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.NEXT_WEEK, Type.TEACHER), (ScheduleFunction) (id, service) -> {
                ImmutablePair<Teacher, Map<LocalDate, List<Lesson>>> response = service.week(id, getNow().plusDays(7), Teacher.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), response.right);
                }
                return ImmutablePair.of(null, null);
            })
            .put(p(Action.NEXT_WEEK, Type.ROOM), (ScheduleFunction) (id, service) -> {
                ImmutablePair<Room, Map<LocalDate, List<Lesson>>> response = service.week(id, getNow().plusDays(7), Room.class);
                if (response.left != null) {
                    return ImmutablePair.of(response.left.getName(), response.right);
                }
                return ImmutablePair.of(null, null);
            })
            .build();

    private static LocalDate getNow() {
//        return LocalDate.of(2019, 4, 22);
        return LocalDate.now();
    }

    public static ScheduleFunction getFor(ScheduleCommandData commandData) {
        return choices.getOrDefault(ImmutablePair.of(commandData.getAction(), commandData.getType()),
                (id, service) -> ImmutablePair.of(null, null));
    }

    public static ImmutablePair<Action, Type> p(Action action, Type type) {
        return ImmutablePair.of(action, type);
    }

    public interface ScheduleFunction extends BiFunction<Long, LessonService, ImmutablePair<String, Map<LocalDate, List<Lesson>>>> {
    }

}
