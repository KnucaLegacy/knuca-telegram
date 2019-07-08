package com.theopus.telegram.bot;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TelegramSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TelegramSender.class);

    public TelegramSender() {
    }

    public void send(Bot bot, TelegramRequest req, TelegramResponse res) throws TelegramApiException {
        if (res.next() == null && res.head() == null) {
            sendOne(bot, req, res);
            return;
        }
        TelegramResponse current = res;

        while (current != null) {
            sendOne(bot, req, current);
            current = current.next();
        }
    }

    private void sendOne(Bot bot, TelegramRequest req, TelegramResponse res) throws TelegramApiException {
        if (res.isUpdate()) {
            EditMessageText msg = new EditMessageText();
            msg.setMessageId(req.isCallback()
                    ? req.getOriginalUpdate().getCallbackQuery().getMessage().getMessageId() : req.getOriginalUpdate().getMessage().getMessageId());
            sendEdit(bot, req, res, msg);
        } else {
            sendNew(bot, req, res, new SendMessage());
        }


    }

    private void sendEdit(Bot bot, TelegramRequest req, TelegramResponse res, EditMessageText msg) {
        msg.setChatId(req.getChat().getId());
        if (res.getBody() != null && !res.getBody().isEmpty()) {
            if (res.withMarkup()) {
                msg.setParseMode("HTML");
            }
            msg.setText(res.getBody());
        } else {
            return;
        }
        if (!res.getButtons().isEmpty()) {
            msg.setChatId(req.getChat().getId());
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            List<InlineKeyboardButton> row = new ArrayList<>();
            keyboard.add(row);
            int length = 0;

            for (InlineKeyboardButton button : res.getButtons()) {
                if ((length += button.getText().length()) >= 24 || row.size() >= 4) {
                    row = new ArrayList<>();
                    keyboard.add(row);
                    length = button.getText().length();
                }
                row.add(button);
            }
            keyboardMarkup.setKeyboard(keyboard);
            msg.setReplyMarkup(keyboardMarkup);
        }
        bot.sendAsync(msg);
    }

    private void sendNew(Bot bot, TelegramRequest req, TelegramResponse res, SendMessage msg) {
        msg.setChatId(req.getChat().getId());
        if (res.getBody() != null && !res.getBody().isEmpty()) {
            if (res.withMarkup()) {
                msg.setParseMode("HTML");
            }
            msg.setText(res.getBody());
        } else {
            return;
        }
        if (!res.getButtons().isEmpty()) {
            msg.setChatId(req.getChat().getId());
            InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

            List<InlineKeyboardButton> row = new ArrayList<>();
            keyboard.add(row);
            int length = 0;

            for (InlineKeyboardButton button : res.getButtons()) {
                if ((length += button.getText().length()) >= 24 || row.size() >= 4) {
                    row = new ArrayList<>();
                    keyboard.add(row);
                    length = button.getText().length();
                }
                row.add(button);
            }
            keyboardMarkup.setKeyboard(keyboard);
            msg.setReplyMarkup(keyboardMarkup);
        }
        bot.sendAsync(msg);
    }
}
