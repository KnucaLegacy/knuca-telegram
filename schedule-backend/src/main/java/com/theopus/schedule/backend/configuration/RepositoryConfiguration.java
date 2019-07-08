package com.theopus.schedule.backend.configuration;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import com.theopus.entity.schedule.Building;
import com.theopus.entity.schedule.Faculty;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Teacher;
import com.theopus.schedule.backend.repository.DepartmentRepository;
import com.theopus.schedule.backend.repository.GroupRepository;
import com.theopus.schedule.backend.repository.JdbcBuildingRepository;
import com.theopus.schedule.backend.repository.JdbcDepartmentRepository;
import com.theopus.schedule.backend.repository.JdbcFacultyRepository;
import com.theopus.schedule.backend.repository.JdbcGroupLessonRepository;
import com.theopus.schedule.backend.repository.JdbcRepositoryGroups;
import com.theopus.schedule.backend.repository.JdbcRepositoryRoom;
import com.theopus.schedule.backend.repository.JdbcRepositoryTeachers;
import com.theopus.schedule.backend.repository.JdbcRoomLessonRepository;
import com.theopus.schedule.backend.repository.JdbcTeacherLessonRepository;
import com.theopus.schedule.backend.repository.Repository;
import com.theopus.schedule.backend.repository.RoomRepository;
import com.theopus.schedule.backend.repository.TeacherRepository;
import com.theopus.schedule.backend.service.LessonService;

@Configuration
public class RepositoryConfiguration {

    @Value("/dao/groups.sql")
    private String allGroupQueryFile;
    @Value("/dao/groups.by.id.sql")
    private String groupByIdQueryFile;
    @Value("/dao/groups.by.faculty.and.course.sql")
    private String queryByFacultyAndYos;
    @Value("/dao/rooms.sql")
    private String allRoomQueryFile;
    @Value("/dao/rooms.by.id.sql")
    private String roomByIdQueryFile;
    @Value("/dao/teachers.sql")
    private String allTeachersQueryFile;
    @Value("/dao/teachers.by.id.sql")
    private String teacherByIdQueryFile;
    @Value("/dao/group.lessons.sql")
    private String lessonsByGroupQueryFile;
    @Value("/dao/room.lessons.sql")
    private String lessonsByRoomQueryFile;
    @Value("/dao/teacher.lessons.sql")
    private String lessonsByTeacherQueryFile;
    @Value("/dao/faculties.sql")
    private String queryFaculties;
    @Value("/dao/departmets.sql")
    private String departmentsSql;
    @Value("/dao/building.sql")
    private String buildingsSql;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName("org.firebirdsql.jdbc.FBDriver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setConnectionProperties("WireCrypt=Enabled");
        dataSource.setConnectionProperties("AuthServer=Srp,Legacy_Auth");
        dataSource.setInitialSize(1);
        dataSource.setMaxActive(4);
        return dataSource;
    }

    @Bean
    public GroupRepository groupRepo(DataSource dataSource) throws IOException {
        return new JdbcRepositoryGroups(dataSource, fileToString(allGroupQueryFile), fileToString(groupByIdQueryFile), fileToString(queryByFacultyAndYos));
    }

    @Bean
    public TeacherRepository teacherRepo(DataSource dataSource) throws IOException {
        return new JdbcRepositoryTeachers(dataSource, fileToString(allTeachersQueryFile), fileToString(teacherByIdQueryFile));
    }
    @Bean
    public DepartmentRepository departmentRepo(DataSource dataSource) throws IOException {
        return new JdbcDepartmentRepository(dataSource, fileToString(departmentsSql));
    }

    @Bean
    public Repository<Faculty> facultyRepo(DataSource dataSource) throws IOException {
        return new JdbcFacultyRepository(dataSource, fileToString(queryFaculties));
    }

    @Bean
    public Repository<Building> buildingRepository(DataSource dataSource) throws IOException {
        return new JdbcBuildingRepository(dataSource, fileToString(buildingsSql));
    }

    @Bean
    public RoomRepository roomRepo(DataSource dataSource) throws IOException {
        return new JdbcRepositoryRoom(dataSource, fileToString(allRoomQueryFile), fileToString(roomByIdQueryFile));
    }

    private String fileToString(String resource) throws IOException {
        return new String(FileCopyUtils.copyToByteArray(new ClassPathResource(resource).getInputStream()), StandardCharsets.UTF_8);
    }

    @Bean
    public LessonService lessonService(Repository<Room> roomRepo, Repository<Group> groupRepo, Repository<Teacher> teacherRepo,
                                       DataSource dataSource) throws IOException {
        return new LessonService(roomRepo, groupRepo, teacherRepo,
                new JdbcRoomLessonRepository(dataSource, fileToString(lessonsByRoomQueryFile)),
                new JdbcGroupLessonRepository(dataSource, fileToString(lessonsByGroupQueryFile)),
                new JdbcTeacherLessonRepository(dataSource, fileToString(lessonsByTeacherQueryFile))
        );
    }

}
