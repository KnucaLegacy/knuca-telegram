package com.theopus.telegram;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.enums.LessonOrder;
import com.theopus.entity.schedule.enums.LessonType;
import com.theopus.telegram.handler.entity.ScheduleCommandData;
import com.theopus.telegram.handler.entity.Type;

public class FormatManager {

    private ImmutableMap orders = new ImmutableMap.Builder<>()
            .put(LessonOrder.FIRST, "9:00")
            .put(LessonOrder.SECOND, "10:30")
            .put(LessonOrder.THIRD, "12:20")
            .put(LessonOrder.FOURTH, "13:50")
            .put(LessonOrder.FIFTH, "15:20")
            .put(LessonOrder.SIXTH, "16:50")
            .put(LessonOrder.SEVENTH, "18:20")
            .put(LessonOrder.EIGHTH, "19:50")
            .build();

    private ImmutableMap types = new ImmutableMap.Builder<>()
            .put(LessonType.LECTURE, "Лекция")
            .put(LessonType.PRACTICE, "Практика")
            .put(LessonType.LAB, "Лабораторная")
            .put(LessonType.EXAM, "Екзамен")
            .put(LessonType.CONSULTATION, "Консультация")
            .put(LessonType.FACULTY, "Факультатив")
            .build();

    private ImmutableMap<ScheduleCommandData.Action, String> actions =
            new ImmutableMap.Builder<ScheduleCommandData.Action, String>()
                    .put(ScheduleCommandData.Action.TODAY, "Сегодня")
                    .put(ScheduleCommandData.Action.TOMORROW, "Завтра")
                    .put(ScheduleCommandData.Action.WEEK, "Неделя")
                    .put(ScheduleCommandData.Action.NEXT_WEEK, "След. Неделя")
                    .build();

    private ImmutableMap<Type, String> searchTypes = ImmutableMap.of(
            Type.GROUP, "Группа",
            Type.TEACHER, "Преподаватель",
            Type.ROOM, "Аудитория");

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE - dd/MM/yyyy", new Locale("ru", "RU"));

    public String format(LessonOrder order) {
        return String.valueOf(orders.getOrDefault(order, "Вне расписания"));
    }

    public String format(LessonType type) {
        return String.valueOf(types.getOrDefault(type, "."));
    }

    public String formatWithDow(LocalDate day) {
        String result = formatter.format(day);
        return result.replace(String.valueOf(result.charAt(0)), String.valueOf(result.charAt(0)).toUpperCase());
    }

    public String format(Set<Room> rooms) {
        return rooms.stream().map(Room::getName).map(s -> "ауд. " + s).collect(Collectors.joining(", "));
    }

    public String format(ScheduleCommandData.Action action) {
        return actions.get(action);
    }

    public String searchStartTitle() {
        return "Выбери категорию";
    }

    public String format(Type type) {
        return searchTypes.get(type);
    }

    public String selectFaculty() {
        return "Факультет";
    }

    public String selectYos() {
        return "Курс";
    }

    public String selectGroup() {
        return "Группа";
    }

    public String selectDept() {
        return "Кафедра";
    }

    public String selectBuilding() {
        return "Корпус";
    }

    public String back() {
        return "<< Назад";
    }

    public String help() {
        return
                "Привет!\n"
                        + "\n"
                        + "Как получить расписание?\n"
                        + "Просто напиши мне группу/преподавателя/аудиторию.\n"
                        + "Или напиши команду /search и следуй меню!\n"
                        + "Бот работает в конфах, так что можете доавлять его в беседы групп.\n"
                        + "\n"
                        + "Спасибо и удачи!"
                        + "\n"
                        + "Вопросы/предложения? Пиши <a href=\"tg://user?name=theopus5\">сюда</a>."
                ;
    }
}
