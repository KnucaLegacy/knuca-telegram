package com.theopus.schedule.backend.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.Repository;
import com.theopus.schedule.backend.search.Storage;
import com.theopus.schedule.backend.search.StorageUpdater;

@Configuration
@Import(RepositoryConfiguration.class)
public class StorageConfiguration {


    @Bean
    public Storage storage() throws IOException {
        return new Storage();
    }

    @Bean
    public StorageUpdater storageUpdater(Repository<Group> groupRepo,
                                         Repository<Teacher> teacherRepo,
                                         Repository<Room> roomRepo,
                                         Storage storage) throws IOException {
        return new StorageUpdater(groupRepo, roomRepo, teacherRepo, storage);
    }
}
