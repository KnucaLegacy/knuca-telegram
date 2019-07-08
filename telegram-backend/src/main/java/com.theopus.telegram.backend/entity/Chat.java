package com.theopus.telegram.backend.entity;

import java.time.LocalDateTime;

public class Chat {

    private Long id;
    private Type type;
    private LocalDateTime lastActive;
    private Integer lastUserActive;

    public Chat() {
    }

    public Chat(Long id, Type type, Integer lastUserActive) {
        this.id = id;
        this.type = type;
        this.lastUserActive = lastUserActive;
        this.lastActive = LocalDateTime.now();

    }

    public Chat(org.telegram.telegrambots.meta.api.objects.Chat chat, org.telegram.telegrambots.meta.api.objects.User from) {
        this(chat.getId(), resolve(chat), from.getId());
    }

    private static Type resolve(org.telegram.telegrambots.meta.api.objects.Chat chat) {
        if (chat.isUserChat()) {
            return Type.PRIVATE;
        }
        if (chat.isGroupChat()) {
            return Type.GROUP;
        }
        if (chat.isChannelChat()) {
            return Type.CHANNEL;
        }
        if (chat.isSuperGroupChat()) {
            return Type.SUPER_GROUP;
        }
        return null;
    }

    public enum Type {
        PRIVATE,
        GROUP,
        SUPER_GROUP,
        CHANNEL
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public LocalDateTime getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }

    public Integer getLastUserActive() {
        return lastUserActive;
    }

    public void setLastUserActive(Integer lastUserActive) {
        this.lastUserActive = lastUserActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Chat chat = (Chat) o;

        if (id != null ? !id.equals(chat.id) : chat.id != null) return false;
        if (type != chat.type) return false;
        if (lastActive != null ? !lastActive.equals(chat.lastActive) : chat.lastActive != null) return false;
        return lastUserActive != null ? lastUserActive.equals(chat.lastUserActive) : chat.lastUserActive == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (lastActive != null ? lastActive.hashCode() : 0);
        result = 31 * result + (lastUserActive != null ? lastUserActive.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Chat{" +
                "id=" + id +
                ", type=" + type +
                ", lastActive=" + lastActive +
                ", lastUserActive=" + lastUserActive +
                '}';
    }
}
