package com.theopus.schedule.backend.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.theopus.entity.schedule.Room;

public class JdbcRepositoryRoom implements Repository<Room> {

    private final JdbcTemplate template;
    private final String query;
    private final String queryById;

    public JdbcRepositoryRoom(DataSource dataSource, String query, String queryById) {
        template = new JdbcTemplate(dataSource);
        this.query = query;
        this.queryById = queryById;
    }

    @Override
    public List<Room> all() {
        return template.query(query, getRoomRowMapper());
    }

    private RowMapper<Room> getRoomRowMapper() {
        return (rs, rowNum) -> {
            Room room = new Room();
            room.setId(rs.getLong("ID"));
            room.setName("ауд. " + rs.getString("NAME"));
            return room;
        };
    }

    @Override
    public Room get(Long id) {
        try {
            return template.queryForObject(queryById, new Object[]{id}, getRoomRowMapper());
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
