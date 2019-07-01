package com.theopus.schedule.backend.repository;

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
import com.theopus.entity.schedule.Room;

public class JdbcRoomLessonRepository implements LessonRepository<Room> {

    private final String query;
    private final NamedParameterJdbcTemplate template;

    public JdbcRoomLessonRepository(DataSource dataSource, String query) {
        template = new NamedParameterJdbcTemplate(dataSource);
        this.query = query;
    }


    @Override
    public Map<LocalDate, List<Lesson>> range(Room room, LocalDate from, LocalDate to) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("LANG", 3)
                .addValue("ROOM_ID", room.getId())
                .addValue("DATE_FROM", from.toString() + " 00:00:00")
                .addValue("DATE_TO", to.toString() + " 00:00:00");

        return template.query(query, parameters, JdbcFirebaseHelpers.lessonRowMapper()).stream().collect(Collectors.groupingBy(Lesson::getDate));
    }

    @Override
    public List<Lesson> at(Room room, LocalDate date) {
        return range(room, date, date).getOrDefault(date, Collections.emptyList());
    }
}
