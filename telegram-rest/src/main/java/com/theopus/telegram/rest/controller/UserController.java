package com.theopus.telegram.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theopus.telegram.backend.entity.User;
import com.theopus.telegram.backend.repository.UserRepository;

@RestController()
@RequestMapping("/api/telegram/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<User>> all() {
        return ResponseEntity.ok(repository.all().collect(Collectors.toList()));
    }
}
