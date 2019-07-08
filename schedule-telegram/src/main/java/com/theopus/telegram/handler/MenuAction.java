package com.theopus.telegram.handler;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.theopus.telegram.FormatManager;
import com.theopus.telegram.TelegramSerDe;
import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;
import com.theopus.telegram.handler.entity.SearchCommand;

public interface MenuAction<T, P, F> {
    Predicate<T> predicate();

    Function<T, Stream<P>> actionsSupplier();

    BiFunction<T, Stream<P>, Stream<ImmutablePair<String, F>>> buttonsFormatter();

    Function<T, String> titleProducer();

    default BiFunction<Stream<ImmutablePair<String, F>>, TelegramSerDe, Stream<InlineKeyboardButton>> commandMapper() {
        return (sc, serDe) -> sc.map(st -> {
            return new InlineKeyboardButton(st.left)
                    .setCallbackData(serDe.serializeForCommand(targetCommand(), st.right));
        });
    }

    Function<T, T> back();

    String command();

    String targetCommand();

    Predicate<T> update();

    default TelegramResponse process(T t, TelegramRequest request, TelegramSerDe serDe, FormatManager formatManager){
        Stream supply = actionsSupplier().apply(t);
        Stream<ImmutablePair<String, SearchCommand>> buttons = buttonsFormatter().apply(t, supply);
        List<InlineKeyboardButton> search = buttons
                .map(sc -> new InlineKeyboardButton(sc.getKey())
                        .setCallbackData(serDe.serializeForCommand(targetCommand(), sc.getRight())))
                .collect(Collectors.toList());
        T tBack  = back().apply(t);
        if (tBack != null) {
            search.add(new InlineKeyboardButton(formatManager.back())
                    .setCallbackData(serDe.serializeForCommand(command(), tBack)));
        }

        String title = titleProducer().apply(t);
        TelegramResponse response = TelegramResponse
                .buttons(search)
                .appendBold(title);
        if (update().test(t) || request.isCallback()) {
            response.update();
        }
        return response;
    }

    static <T, P> MenuAction<T, P, T> ofSame(
            Predicate<T> predicate,
            Function<T, Stream<P>> actionsSupplier,
            BiFunction<T, Stream<P>, Stream<ImmutablePair<String, T>>> buttonsFormatter,
            Function<T, String> titleProducer,
            Function<T, T> back,
            String command
    ) {
        return of(predicate, actionsSupplier, buttonsFormatter, titleProducer, back, t -> false, command, command);
    }

    static <T, P> MenuAction<T, P, T> ofSame(
            Predicate<T> predicate,
            Function<T, Stream<P>> actionsSupplier,
            BiFunction<T, Stream<P>, Stream<ImmutablePair<String, T>>> buttonsFormatter,
            Function<T, String> titleProducer,
            Function<T, T> back,
            Predicate<T> update,
            String command
    ) {
        return of(predicate, actionsSupplier, buttonsFormatter, titleProducer, back, update, command, command);
    }

    static <T, P, F> MenuAction<T, P, F> of(
            Predicate<T> predicate,
            Function<T, Stream<P>> actionsSupplier,
            BiFunction<T, Stream<P>, Stream<ImmutablePair<String, F>>> buttonsFormatter,
            Function<T, String> titleProducer,
            Function<T, T> back,
            String command
    ) {
        return of(predicate, actionsSupplier, buttonsFormatter, titleProducer, back, t -> false, command, command);
    }

    static <T, P, F> MenuAction<T, P, F> of(
            Predicate<T> predicate,
            Function<T, Stream<P>> actionsSupplier,
            BiFunction<T, Stream<P>, Stream<ImmutablePair<String, F>>> buttonsFormatter,
            Function<T, String> titleProducer,
            Function<T, T> back,
            Predicate<T> update,
            String command,
            String targetCommand
    ) {
        return new MenuAction<T, P, F>() {
            @Override
            public Predicate<T> predicate() {
                return predicate;
            }

            @Override
            public Function<T, Stream<P>> actionsSupplier() {
                return actionsSupplier;
            }

            @Override
            public BiFunction<T, Stream<P>, Stream<ImmutablePair<String, F>>> buttonsFormatter() {
                return buttonsFormatter;
            }

            @Override
            public Function<T, String> titleProducer() {
                return titleProducer;
            }

            @Override
            public Function<T, T> back() {
                return back;
            }

            @Override
            public String command() {
                return command;
            }

            @Override
            public String targetCommand() {
                return targetCommand;
            }

            @Override
            public Predicate<T> update() {
                return update;
            }
        };
    }
}
