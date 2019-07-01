package com.theopus.telegram;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import com.google.common.collect.ImmutableMap;
import com.theopus.entity.schedule.enums.LessonOrder;
import com.theopus.entity.schedule.enums.LessonType;

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
}
