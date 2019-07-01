package com.theopus.telegram.bot;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class TelegramRequest {

    private final User from;
    private final Chat chat;
    private final String originalMessage;
    private final Update originalUpdate;
    private boolean callback;
    private String data;
    private String command;
    private Set<String> mentions;

    public TelegramRequest(User from, Chat chat, String originalMessage, Update originalUpdate) {
        this.from = from;
        this.chat = chat;
        this.originalMessage = originalMessage;
        this.originalUpdate = originalUpdate;
    }

    public User getFrom() {
        return from;
    }

    public Chat getChat() {
        return chat;
    }

    public String getData() {
        return data;
    }

    public Update getOriginalUpdate() {
        return originalUpdate;
    }

    TelegramRequest setCallback(boolean callback) {
        this.callback = callback;
        return this;
    }

    public boolean isCallback() {
        return callback;
    }

    public String getCommand() {
        return command;
    }

    TelegramRequest setCommand(String command) {
        this.command = command;
        return this;
    }

    public boolean isCommand() {
        return command != null;
    }

    TelegramRequest setData(String data) {
        this.data = data;
        return this;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    TelegramRequest addMention(String mention) {
        if (mentions == null) {
            this.mentions = new LinkedHashSet<>();
        }
        mentions.add(mention);
        return this;
    }

    public Stream<String> getMentions() {
        return mentions == null ? Stream.empty() : mentions.stream();
    }

    public boolean hasMentions() {
        return mentions != null && !mentions.isEmpty();
    }

    @Override
    public String toString() {
        return "TelegramRequest{" +
                "from=" + from +
                ", chat=" + chat +
                ", originalMessage='" + originalMessage + '\'' +
                ", originalUpdate=" + originalUpdate +
                ", callback=" + callback +
                ", data='" + data + '\'' +
                ", command='" + command + '\'' +
                ", mentions=" + mentions +
                '}';
    }

    public boolean hasMention(String botUsername) {
        return mentions != null && mentions.contains(botUsername);
    }
}
