package com.theopus.schedule.rest.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theopus.entity.schedule.Room;
import com.theopus.schedule.backend.repository.Repository;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private Repository<Room> repository;

    @Autowired
    public RoomController(Repository<Room> service) {
        this.repository = service;
    }

    @GetMapping
    public ResponseEntity<Collection<Room>> all() {
        return ResponseEntity.ok(repository.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> byId(@PathVariable Long id) {
        Room room = repository.get(id);
        if (Objects.isNull(room)) {
            throw new EntityNotFoundException("Not found Room with id " + id);
        }
        return ResponseEntity.ok(room);
    }
}
