package com.theopus.telegram.handler;

import java.time.LocalDateTime;

import com.theopus.telegram.backend.entity.Chat;
import com.theopus.telegram.backend.entity.User;
import com.theopus.telegram.backend.repository.ChatRepository;
import com.theopus.telegram.backend.repository.UserRepository;
import com.theopus.telegram.bot.TelegramHandlerInterceptor;
import com.theopus.telegram.bot.TelegramRequest;
import com.theopus.telegram.bot.TelegramResponse;

public class UserMonitoringInterceptor implements TelegramHandlerInterceptor {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    public UserMonitoringInterceptor(UserRepository userRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
    }

    @Override
    public boolean preHandle(TelegramRequest request) {
        Chat chat = chatRepository.get(request.getChat().getId());
        if (chat == null) {
            chat = new Chat(request.getChat(), request.getFrom());
        } else {
            chat.setLastActive(LocalDateTime.now());
            chat.setLastUserActive(request.getFrom().getId());
        }
        chatRepository.add(chat);

        User user = userRepository.get(request.getFrom().getId());
        if (user == null) {
            user = new User(request.getFrom(), request.getChat());
        } else {
            user.setLastActiveChat(request.getChat().getId());
        }
        userRepository.add(user);
        return true;
    }

    @Override
    public void postHandle(TelegramRequest request, TelegramResponse response) {

    }
}
