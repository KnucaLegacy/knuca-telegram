package com.theopus.schedule.backend.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.jdbc.core.RowMapper;

import com.theopus.entity.schedule.Course;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Subject;
import com.theopus.entity.schedule.Teacher;
import com.theopus.entity.schedule.enums.LessonOrder;
import com.theopus.entity.schedule.enums.LessonType;

public class JdbcFirebaseHelpers {
    private static Set<Group> getGroups(ResultSet rs) throws SQLException {
        return Stream.of(rs.getString("GROUPS")
                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Group::new)
                .collect(Collectors.toSet());
    }

    static Set<Room> getRooms(ResultSet rs) throws SQLException {
        return Stream.of(rs.getString("ROOMS")
                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Room::new)
                .collect(Collectors.toSet());
    }

    static Set<Teacher> getTeachers(ResultSet rs) throws SQLException {
        return Stream.of(rs.getString("TEACHERS")
                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Teacher::new)
                .collect(Collectors.toSet());
    }

    static RowMapper<Lesson> lessonRowMapper() {
        return (rs, rowNum) -> {
            Lesson lesson = new Lesson();
            lesson.setDate(rs.getDate("DAT").toLocalDate());
            lesson.setGroups(getGroups(rs));
            lesson.setRooms(getRooms(rs));
            lesson.setOrder(LessonOrder.values()[rs.getInt("LESSON_ORDER")]);
            Course course = new Course();
            course.setSubject(new Subject(rs.getString("FNAME")));
            course.setType(LessonType.values()[rs.getInt("LTYPE")]);
            course.setTeachers(getTeachers(rs));
            lesson.setCourse(course);
            return lesson;
        };
    }
}
