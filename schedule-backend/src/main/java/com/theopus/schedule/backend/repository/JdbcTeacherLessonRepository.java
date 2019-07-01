package com.theopus.schedule.backend.repository;

import static com.theopus.schedule.backend.repository.JdbcFirebaseHelpers.lessonRowMapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.theopus.entity.schedule.Lesson;
import com.theopus.entity.schedule.Teacher;

public class JdbcTeacherLessonRepository implements LessonRepository<Teacher> {

    private final NamedParameterJdbcTemplate template   ;
    private final String query;

    public JdbcTeacherLessonRepository(DataSource dataSource, String query) {
        template = new NamedParameterJdbcTemplate(dataSource);
        this.query = query;
    }

    @Override
    public Map<LocalDate, List<Lesson>> range(Teacher teacher, LocalDate from, LocalDate to) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("LANG", 3)
                .addValue("TEACHER_ID", teacher.getId())
                .addValue("DATE_FROM", from.toString() + " 00:00:00")
                .addValue("DATE_TO", to.toString() + " 00:00:00");

        return template.query(query, parameters, lessonRowMapper()).stream().collect(Collectors.groupingBy(Lesson::getDate));
    }

    @Override
    public List<Lesson> at(Teacher teacher, LocalDate date) {
        return range(teacher, date, date).getOrDefault(date, Collections.emptyList());
    }
}
