package com.theopus.search;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.theopus.entitiy.schedule.Group;
import com.theopus.entitiy.schedule.Room;
import com.theopus.entitiy.schedule.Teacher;

public class StorageUpdater {
    private static final Logger LOGGER = LoggerFactory.getLogger(StorageUpdater.class);

    private final RestRepository<Group> groups;
    private final RestRepository<Room> rooms;
    private final RestRepository<Teacher> teachers;
    private final Storage storage;

    public StorageUpdater(RestRepository<Group> groups, RestRepository<Room> rooms, RestRepository<Teacher> teachers, Storage storage) throws IOException {
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
