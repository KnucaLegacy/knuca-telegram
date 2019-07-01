package com.theopus.schedule.backend;

import java.io.IOException;
import java.time.LocalDate;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.theopus.schedule.backend.configuration.RepositoryConfiguration;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.JdbcLessonsRepository;
import com.theopus.schedule.backend.repository.LessonRepository;
import com.theopus.schedule.backend.search.Storage;
import com.theopus.schedule.backend.configuration.StorageConfiguration;

public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(RepositoryConfiguration.class, StorageConfiguration.class);

        Storage storage = ctx.getBean(Storage.class);
        storage.search("459").stream().forEach(System.out::println);

        LessonRepository lessonsRepository = ctx.getBean(JdbcLessonsRepository.class);

        Group group = new Group();
        group.setId(302L);
        Teacher teacher = new Teacher();
        teacher.setId(847L);
        Room room = new Room();
        room.setId(231L);
        System.out.println(lessonsRepository.byGroup(group, LocalDate.of(2019, 3, 5)));
        System.out.println(lessonsRepository.byTeacher(teacher, LocalDate.of(2019, 3, 6)));
        System.out.println(lessonsRepository.byRoom(room, LocalDate.of(2019, 3, 6)));
    }
}
