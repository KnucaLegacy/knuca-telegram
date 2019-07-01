package com.theopus.schedule.backend.search;

import java.io.IOException;
import java.util.List;

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

    public void process() {
        try {
            LOGGER.info("Updating storage...");
            storage.emptyWrite();

            List<Group> groupsQ = groups.all();
            LOGGER.info("Queried groups {}", groupsQ.size());
            storage.storeGroups(groupsQ);
            LOGGER.info("Stored groups.");

            List<Teacher> teachersQ = teachers.all();
            LOGGER.info("Queried teachers {}", teachersQ.size());
            storage.storeTeachers(teachersQ);
            LOGGER.info("Stored teachers.");
            List<Room> roomQ = rooms.all();
            LOGGER.info("Queried teachers {}", roomQ.size());
            storage.storeRooms(roomQ);
            LOGGER.info("Stored rooms.");
            LOGGER.info("Finished updating.");
            storage.swap();
        } catch (IOException e) {
            LOGGER.error("{}",e);
            throw new RuntimeException(e);
        }
    }

}
