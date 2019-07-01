package com.theopus.schedule.backend.search;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.Repository;

public class StorageUpdater {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageUpdater.class);

    private final Repository<Group> groups;
    private final Repository<Room> rooms;
    private final Repository<Teacher> teachers;
    private final Storage storage;

    public StorageUpdater(Repository<Group> groups, Repository<Room> rooms, Repository<Teacher> teachers, Storage storage) throws IOException {
        this.groups = groups;
        this.rooms = rooms;
        this.teachers = teachers;
        this.storage = storage;
        process();
    }

    public void process() throws IOException {
        storage.storeGroups(groups.all());
        storage.storeTeachers(teachers.all());
        storage.storeRooms(rooms.all());
    }

}
