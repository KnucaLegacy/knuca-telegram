package com.theopus.telegram.backend.entity;

import org.telegram.telegrambots.meta.api.objects.Chat;

public class User {

    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;
    private Long lastActiveChat;

    public User() {
    }

    public User(Integer id, String firstName, String lastName, String userName, Long lastActiveChat) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.lastActiveChat = lastActiveChat;
    }

    public User(org.telegram.telegrambots.meta.api.objects.User from, Chat chat) {
        this(from.getId(), from.getFirstName(), from.getLastName(), from.getUserName(), chat.getId());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getLastActiveChat() {
        return lastActiveChat;
    }

    public void setLastActiveChat(Long lastActiveChat) {
        this.lastActiveChat = lastActiveChat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (userName != null ? !userName.equals(user.userName) : user.userName != null) return false;
        return lastActiveChat != null ? lastActiveChat.equals(user.lastActiveChat) : user.lastActiveChat == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (lastActiveChat != null ? lastActiveChat.hashCode() : 0);
        return result;
    }
}
