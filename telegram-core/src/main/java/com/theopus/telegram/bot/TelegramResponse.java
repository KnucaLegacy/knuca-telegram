package com.theopus.telegram.bot;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class TelegramResponse {
    private ResponseStatus status;
    private final List<InlineKeyboardButton> buttons;
    private StringBuilder body;
    private boolean markup;
    private TelegramResponse next;
    private TelegramResponse head;

    public TelegramResponse() {
        this.body = new StringBuilder();
        this.status = ResponseStatus.OK;
        this.buttons = new ArrayList<>();
    }

    public static TelegramResponse body(String body) {
        TelegramResponse response = new TelegramResponse();
        response.body = new StringBuilder(body);
        return response;
    }

    public static TelegramResponse buttons(List<InlineKeyboardButton> buttons) {
        TelegramResponse response = new TelegramResponse();
        response.buttons.addAll(buttons);
        return response;
    }

    public static TelegramResponse ofRaw(String body) {
        TelegramResponse response = new TelegramResponse();
        response.addRaw(body);
        return response;
    }

    public TelegramResponse addRaw(String body) {
        this.markup = true;
        this.body.append("<pre>").append(body).append("</pre>");
        return this;
    }

    public String getBody() {
        return body.toString();
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public List<InlineKeyboardButton> getButtons() {
        return buttons;
    }

    @Override
    public String toString() {
        return "TelegramResponse{" +
                "status=" + status +
                ", buttons=" + buttons +
                ", body='" + body + '\'' +
                ", markup=" + markup +
                ", next=" + next +
                '}';
    }

    public boolean withMarkup() {
        return markup;
    }

    public TelegramResponse append() {
        TelegramResponse response = new TelegramResponse();
        TelegramResponse last = last();
        last.next = response;
        last.next.head = last.head == null ? last : last.head;
        return response;
    }

    public TelegramResponse last() {
        if (this.next == null) {
            return this;
        } else {
            return this.last();
        }
    }

    public TelegramResponse head() {
        return head;
    }

    public TelegramResponse next() {
        return next;
    }

    public TelegramResponse addBody(String body) {
        this.body.append(body);
        return this;
    }

    public TelegramResponse appendBold(String body) {
        this.body.append("<b>").append(body).append("</b>");
        markup = true;
        return this;
    }

    public String getBold(String text) {
        return "<b>" + text + "</b>";
    }


}
