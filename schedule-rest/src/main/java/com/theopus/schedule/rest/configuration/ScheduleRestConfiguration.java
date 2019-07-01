package com.theopus.schedule.rest.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.theopus.schedule.backend.configuration.RepositoryConfiguration;
import com.theopus.schedule.rest.controller.GroupController;
import com.theopus.schedule.rest.controller.LessonController;
import com.theopus.schedule.rest.controller.RestExceptionHandler;
import com.theopus.schedule.rest.controller.RoomController;
import com.theopus.schedule.rest.controller.TeacherController;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.LessonRepository;
import com.theopus.schedule.backend.repository.Repository;

@Configuration
@Import(RepositoryConfiguration.class)
public class ScheduleRestConfiguration {

    @Bean
    public GroupController gcontroller(Repository<Group> repository) {
        System.out.println("STARTING");
        return new GroupController(repository);
    }

    @Bean
    public RoomController rcontroller(Repository<Room> repository) {
        return new RoomController(repository);
    }

    @Bean
    public TeacherController tcontroller(Repository<Teacher> repository) {
        return new TeacherController(repository);
    }

    @Bean
    public RestExceptionHandler handler() {
        return new RestExceptionHandler();
    }

    @Bean
    public LessonController lcontroller(Repository<Group> grepository,
                                        Repository<Teacher> trepository,
                                        Repository<Room> rrepository,
                                        LessonRepository lessonRepository
    ) {
        return new LessonController(lessonRepository, grepository, trepository, rrepository);
    }

}
