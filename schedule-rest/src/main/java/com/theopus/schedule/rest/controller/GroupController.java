package com.theopus.schedule.rest.controller;

import java.util.Collection;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theopus.entity.schedule.Group;
import com.theopus.schedule.backend.repository.Repository;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private static final Logger LOG = LoggerFactory.getLogger(GroupController.class);
    private Repository<Group> repository;

    @Autowired
    public GroupController(Repository<Group> service) {
        this.repository = service;
    }

    @GetMapping
    public ResponseEntity<Collection<Group>> all() {

        return ResponseEntity.ok(repository.all());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> byId(@PathVariable Long id) {
        Group group = repository.get(id);
        if (Objects.isNull(group)) {
            throw new EntityNotFoundException("Not found Group with id " + id);
        }
        return ResponseEntity.ok(group);
    }
}
