package com.theopus.schedule.backend.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.theopus.entity.schedule.Faculty;

public class JdbcFacultyRepository implements Repository<Faculty> {

    private final NamedParameterJdbcTemplate template;
    private final String query;

    public JdbcFacultyRepository(DataSource dataSource, String queryAll) {
        template = new NamedParameterJdbcTemplate(dataSource);
        this.query = queryAll;
    }

    @Override
    public List<Faculty> all() {
        return template.query(query, (rs, rowNum) -> new Faculty(
                rs.getInt("ID"),
                rs.getString("FULL_NAME"),
                rs.getString("SHORT_NAME")));
    }

    @Override
    public Faculty get(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ID", id);
        try {
            return template.queryForObject(query
                    .concat(" AND F1=:ID"), namedParameters, (rs, rowNum) -> new Faculty(
                    rs.getInt("ID"),
                    rs.getString("FULL_NAME"),
                    rs.getString("SHORT_NAME")));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
