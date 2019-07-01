package com.theopus.schedule.backend.repository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.theopus.entity.schedule.Course;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Subject;
import com.theopus.entity.schedule.enums.LessonOrder;
import com.theopus.entity.schedule.enums.LessonType;

public class JdbcGroupLessonRepository implements LessonRepository<Group> {

    private final NamedParameterJdbcTemplate template;
    private final String query;

    public JdbcGroupLessonRepository(DataSource dataSource, String queryByGroup) {
        template = new NamedParameterJdbcTemplate(dataSource);
        this.query = queryByGroup;
    }

    @Override
    public List<Lesson> at(Group group, LocalDate date) {
        return range(group, date, date).getOrDefault(date, Collections.emptyList());
    }


    @Override
    public Map<LocalDate, List<Lesson>> range(Group group, LocalDate from, LocalDate to) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("LANG", 3)
                .addValue("GROUP_ID", group.getId())
                .addValue("DATE_FROM", from.toString() + " 00:00:00")
                .addValue("DATE_TO", to.toString() + " 00:00:00");

        return template.query(query, parameters, (rs, rowNum) -> {
            Lesson lesson = new Lesson();
            lesson.setDate(rs.getDate("DAT").toLocalDate());
            lesson.setGroups(new HashSet<Group>() {{
                add(group);
            }});
            lesson.setRooms(JdbcFirebaseHelpers.getRooms(rs));
            lesson.setOrder(LessonOrder.values()[rs.getInt("LESSON_ORDER")]);
            Course course = new Course();
            course.setSubject(new Subject(rs.getString("FNAME")));
            course.setType(LessonType.values()[rs.getInt("LTYPE")]);
            course.setTeachers(JdbcFirebaseHelpers.getTeachers(rs));
            lesson.setCourse(course);
            return lesson;
        }).stream().collect(Collectors.groupingBy(Lesson::getDate));
    }


}
