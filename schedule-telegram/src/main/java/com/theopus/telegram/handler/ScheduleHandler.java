package com.theopus.telegram.handler;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;

import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.LessonRepository;
import com.theopus.schedule.backend.service.LessonService;
import com.theopus.telegram.FormatManager;
import com.theopus.telegram.TelegramSerDe;
import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;
import com.theopus.telegram.bot.handlers.TelegramHandler;
import com.theopus.telegram.handler.entity.ScheduleCommandData;

public class ScheduleHandler implements TelegramHandler {

    public static final String COMMAND = "_get_schedule";
    private TelegramSerDe serDe;
    private final LessonService service;
    private final FormatManager formatManager;

    public ScheduleHandler(TelegramSerDe serDe, LessonService service, FormatManager formatManager) {
        this.serDe = serDe;
        this.service = service;
        this.formatManager = formatManager;
    }

    @Override
    public TelegramResponse handle(TelegramRequest req) {
        if (req.getData().isEmpty()) {
            return TelegramResponse.body("");
        } else {
            ScheduleCommandData scheduleCommand = serDe.deserialize(req.getData(), ScheduleCommandData.class);
            return handle(scheduleCommand);
        }
    }

    private TelegramResponse handle(ScheduleCommandData command) {
        LocalDate from = LocalDate.of(2019, 04, 20);
        LocalDate to = LocalDate.of(2019, 04, 30);





        TelegramResponse response = new TelegramResponse();
        appdendSchedule(response, title, lessons);
        appdendButtons(response, command);
        return response.head();
    }

    private ImmutablePair<String, Map<LocalDate, List<Lesson>>> getLessons(ScheduleCommandData commandData){
        Object query = service.query();
        switch (commandData.getType()){
            case GROUP:
            case TEACHER:
            case ROOM:
        }
    }

    private void appdendButtons(TelegramResponse response, ScheduleCommandData command) {

    }

    private final String headerTemplate = "" +
            "%s\n" +
            "%s\n\n";

    private final String scheduleTemplate = "" +
            "║%s║%s║\n" +
            "║%s║\n" +
            "║%s║%s║\n" +
            "║%s║\n\n";

    private void appdendSchedule(TelegramResponse response, String title, Map<LocalDate, List<Lesson>> lessons) {
        for (Map.Entry<LocalDate, List<Lesson>> dateLessons : lessons.entrySet()) {
            response.addBody(String.format(headerTemplate,
                    response.getBold(title),
                    formatManager.formatWithDow(dateLessons.getKey())));
            setTable(response, dateLessons.getValue());
            response = response.append();
        }
    }

    private void setTable(TelegramResponse response, List<Lesson> value) {
        for (Lesson lesson : value) {
            response.addBody(String.format(scheduleTemplate,
                    //first
                    response.getBold(formatManager.format(lesson.getOrder())),
                    formatManager.format(lesson.getCourse().getType()),
                    //second
                    lesson.getCourse().getSubject().getName(),
                    //third
                    lesson.getRooms().stream().map(Room::getName).collect(Collectors.joining(", ")),
                    lesson.getCourse().getTeachers().stream().map(Teacher::getName).collect(Collectors.joining(",")),
                    //fourth
                    lesson.getGroups().stream().map(Group::getName).collect(Collectors.joining(", "))));
        }
    }

    @Override
    public String command() {
        return COMMAND;
    }
}
