package com.theopus.schedule.backend.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.JdbcLessonsRepository;
import com.theopus.schedule.backend.repository.JdbcRepositoryGroups;
import com.theopus.schedule.backend.repository.JdbcRepositoryRoom;
import com.theopus.schedule.backend.repository.JdbcRepositoryTeachers;
import com.theopus.schedule.backend.repository.LessonRepository;
import com.theopus.schedule.backend.repository.Repository;

@Configuration
public class RepositoryConfiguration {

    @Value("classpath:/dao/groups.sql")
    private Resource allGroupQueryFile;
    @Value("classpath:/dao/groups.by.id.sql")
    private Resource groupByIdQueryFile;
    @Value("classpath:/dao/rooms.sql")
    private Resource allRoomQueryFile;
    @Value("classpath:/dao/rooms.by.id.sql")
    private Resource roomByIdQueryFile;
    @Value("classpath:/dao/teachers.sql")
    private Resource allTeachersQueryFile;
    @Value("classpath:/dao/teachers.by.id.sql")
    private Resource teacherByIdQueryFile;
    @Value("classpath:/dao/group.lessons.sql")
    private Resource lessonsByGroupQueryFile;
    @Value("classpath:/dao/room.lessons.sql")
    private Resource lessonsByRoomQueryFile;
    @Value("classpath:/dao/teacher.lessons.sql")
    private Resource lessonsByTeacherQueryFile;

    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName("org.firebirdsql.jdbc.FBDriver");
        dataSource.setUrl("jdbc:firebirdsql:localhost/3050:mkr");
        dataSource.setUsername("OTKACHOV");
        dataSource.setPassword("Fuffy-20-21");
        dataSource.setConnectionProperties("WireCrypt=Enabled");
        dataSource.setConnectionProperties("AuthServer=Srp,Legacy_Auth");
        dataSource.setInitialSize(1);
//        dataSource.setMaxActive(4);
//        dataSource.setMaxIdle(2);
        try {
            System.out.println(dataSource.getPassword());
            System.out.println(dataSource.getUsername());
            dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dataSource;
    }


    @Bean
    public Repository<Group> groupRepo(DataSource dataSource) throws IOException {
        return new JdbcRepositoryGroups(dataSource, fileToString(allGroupQueryFile), fileToString(groupByIdQueryFile));
    }


    @Bean
    public Repository<Teacher> teacherRepo(DataSource dataSource) throws IOException {
        return new JdbcRepositoryTeachers(dataSource, fileToString(allTeachersQueryFile), fileToString(teacherByIdQueryFile));
    }


    @Bean
    public Repository<Room> roomRepo(DataSource dataSource) throws IOException {
        return new JdbcRepositoryRoom(dataSource, fileToString(allRoomQueryFile), fileToString(roomByIdQueryFile));
    }

    private String fileToString(Resource resource) throws IOException {
        return new String(Files.readAllBytes(resource.getFile().toPath()), StandardCharsets.UTF_8);
    }

    @Bean
    public LessonRepository lessonsRepository(DataSource dataSource) throws IOException {
        return new JdbcLessonsRepository(dataSource,
                fileToString(lessonsByGroupQueryFile),
                fileToString(lessonsByRoomQueryFile),
                fileToString(lessonsByTeacherQueryFile)
        );
    }

}
