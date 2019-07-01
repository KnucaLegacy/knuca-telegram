package com.theopus.telegram.handler;

import org.stringtemplate.v4.ST;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import com.theopus.telegram.FormatManager;
import com.theopus.telegram.TelegramSerDe;

public interface ButtonGenerator<T> {

    public InlineKeyboardButton generate(TelegramSerDe serDe, FormatManager formatManager, T t);
    public InlineKeyboardButton generate(TelegramSerDe serDe, FormatManager formatManager, T t, String title);
}
