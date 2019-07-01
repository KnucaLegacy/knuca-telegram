package com.theopus.schedule.rest.controller;

import java.util.Collection;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.Repository;


@RestController
@RequestMapping("/teachers")
public class TeacherController {

    private Repository<Teacher> repository;

    @Autowired
    public TeacherController(Repository<Teacher> service) {
        this.repository = service;
    }

    @GetMapping
    public ResponseEntity<Collection<Teacher>> all() {
        return ResponseEntity.ok(repository.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> byId(@PathVariable Long id) {
        Teacher teacher = repository.get(id);
        if (Objects.isNull(teacher)) {
            throw new EntityNotFoundException("Not found Teacher with id " + id);
        }
        return ResponseEntity.ok(teacher);
    }
}
