package com.theopus.telegram.handler;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Teacher;
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
    public static final ButtonGenerator<ScheduleCommandData> generator = new ButtonGenerator<ScheduleCommandData>() {

        @Override
        public InlineKeyboardButton generate(TelegramSerDe serDe, FormatManager formatManager,
                ScheduleCommandData commandData) {
            return new InlineKeyboardButton(formatManager.format(commandData.getAction()))
                    .setCallbackData(serDe.serializeForCommand(COMMAND, commandData));
        }

        @Override
        public InlineKeyboardButton generate(TelegramSerDe serDe, FormatManager formatManager,
                ScheduleCommandData commandData, String text) {
            return new InlineKeyboardButton(text)
                    .setCallbackData(serDe.serializeForCommand(COMMAND, commandData));
        }
    };

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
        ImmutablePair<String, Map<LocalDate, List<Lesson>>> result = ScheduleHandlerHelper.getFor(command).apply(
                command.getId(), service);
        TelegramResponse response = new TelegramResponse();
        if (result.left == null || result.left.isEmpty()) {
            return response;
        }
        if (result.right.isEmpty()) {
            response.addBody("Нет пар");
        } else {
            response = appdendSchedule(response, result.left, result.right);
        }
        appdendButtons(response, command, result.left);
        return response.head();
    }

    private void appdendButtons(TelegramResponse response, ScheduleCommandData command, String title) {
        response.addButtons(Arrays.asList(
                generator.generate(serDe, formatManager, new ScheduleCommandData(command.getId(), command.getType(),
                        ScheduleCommandData.Action.TOMORROW)),
                generator.generate(serDe, formatManager, new ScheduleCommandData(command.getId(), command.getType(),
                        ScheduleCommandData.Action.WEEK)),
                generator.generate(serDe, formatManager, new ScheduleCommandData(command.getId(), command.getType(),
                        ScheduleCommandData.Action.NEXT_WEEK)),
                generator.generate(serDe, formatManager, new ScheduleCommandData(command.getId(), command.getType(),
                        ScheduleCommandData.Action.TODAY))
        ));
    }

    private final String headerTemplate = "" +
            "%s\n" +
            "%s\n\n";

    private final String scheduleTemplate = "" +
            "║%s║%s║\n" +
            "║%s║\n" +
            "║%s║%s║\n" +
            "║%s║\n\n";

    private TelegramResponse appdendSchedule(TelegramResponse response, String title,
            Map<LocalDate, List<Lesson>> lessons) {
        for (Map.Entry<LocalDate, List<Lesson>> dateLessons : lessons.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .collect(Collectors.toList())) {
            response = response.append();
            response.addBody(String.format(headerTemplate,
                    response.getBold(title),
                    formatManager.formatWithDow(dateLessons.getKey())));
            setTable(response, dateLessons.getValue());
        }
        return response;
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
                    formatManager.format(lesson.getRooms()),
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
