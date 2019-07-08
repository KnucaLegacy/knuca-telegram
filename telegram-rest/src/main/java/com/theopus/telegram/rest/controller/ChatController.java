package com.theopus.telegram.rest.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theopus.telegram.backend.entity.Chat;
import com.theopus.telegram.backend.repository.ChatRepository;

@RestController()
@RequestMapping("/api/telegram/chats")
public class ChatController {

    private final ChatRepository repository;

    public ChatController(ChatRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Chat>> all() {
        return ResponseEntity.ok(repository.all().collect(Collectors.toList()));
    }
}
