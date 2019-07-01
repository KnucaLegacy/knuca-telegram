package com.theopus.schedule.backend.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.theopus.entity.schedule.Course;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Room;
import com.theopus.entity.schedule.Subject;
import com.theopus.entity.schedule.Teacher;
import com.theopus.entity.schedule.enums.LessonOrder;
import com.theopus.entity.schedule.enums.LessonType;

public class JdbcLessonsRepository implements LessonRepository {

    private final NamedParameterJdbcTemplate template;
    private final String queryByGroup;
    private final String queryByRoom;
    private final String queryByTeacher;

    public JdbcLessonsRepository(DataSource dataSource, String queryByGroup, String queryByRoom, String queryByTeacher) {
        template = new NamedParameterJdbcTemplate(dataSource);
        this.queryByGroup = queryByGroup;
        this.queryByRoom = queryByRoom;
        this.queryByTeacher = queryByTeacher;
    }

    @Override
    public Map<LocalDate, List<Lesson>> byGroup(Group group, LocalDate from, LocalDate to) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("LANG", 3)
                .addValue("GROUP_ID", group.getId())
                .addValue("DATE_FROM", from.toString() + " 00:00:00")
                .addValue("DATE_TO", to.toString() + " 00:00:00");

        return template.query(queryByGroup, parameters, (rs, rowNum) -> {
            Lesson lesson = new Lesson();
            lesson.setDate(rs.getDate("DAT").toLocalDate());
            lesson.setGroups(new HashSet<Group>() {{
                add(group);
            }});
            lesson.setRooms(getRooms(rs));
            lesson.setOrder(LessonOrder.values()[rs.getInt("LESSON_ORDER")]);
            Course course = new Course();
            course.setSubject(new Subject(rs.getString("FNAME")));
            course.setType(LessonType.values()[rs.getInt("LTYPE")]);
            course.setTeachers(getTeachers(rs));
            lesson.setCourse(course);
            return lesson;
        }).stream().collect(Collectors.groupingBy(Lesson::getDate));
    }

    @Override
    public Map<LocalDate, List<Lesson>> byTeacher(Teacher teacher, LocalDate from, LocalDate to) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("LANG", 3)
                .addValue("TEACHER_ID", teacher.getId())
                .addValue("DATE_FROM", from.toString() + " 00:00:00")
                .addValue("DATE_TO", to.toString() + " 00:00:00");

        return template.query(queryByTeacher, parameters, lessonRowMapper()).stream().collect(Collectors.groupingBy(Lesson::getDate));
    }

    @Override
    public Map<LocalDate, List<Lesson>> byRoom(Room room, LocalDate from, LocalDate to) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("LANG", 3)
                .addValue("ROOM_ID", room.getId())
                .addValue("DATE_FROM", from.toString() + " 00:00:00")
                .addValue("DATE_TO", to.toString() + " 00:00:00");

        return template.query(queryByRoom, parameters, lessonRowMapper()).stream().collect(Collectors.groupingBy(Lesson::getDate));
    }

    private RowMapper<Lesson> lessonRowMapper() {
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

    private Set<Room> getRooms(ResultSet rs) throws SQLException {
        return Stream.of(rs.getString("ROOMS")
                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Room::new)
                .collect(Collectors.toSet());
    }

    private Set<Group> getGroups(ResultSet rs) throws SQLException {
        return Stream.of(rs.getString("GROUPS")
                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Group::new)
                .collect(Collectors.toSet());
    }

    private Set<Teacher> getTeachers(ResultSet rs) throws SQLException {
        return Stream.of(rs.getString("TEACHERS")
                .split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Teacher::new)
                .collect(Collectors.toSet());
    }

    @Override
    public List<Lesson> byGroup(Group group, LocalDate date) {
        return byGroup(group, date, date).getOrDefault(date, Collections.emptyList());
    }

    @Override
    public List<Lesson> byTeacher(Teacher teacher, LocalDate date) {
        return byTeacher(teacher, date, date).getOrDefault(date, Collections.emptyList());
    }

    @Override
    public List<Lesson> byRoom(Room room, LocalDate date) {
        return byRoom(room, date, date).getOrDefault(date, Collections.emptyList());
    }
}
