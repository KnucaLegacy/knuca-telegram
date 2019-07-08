package com.theopus.schedule.backend.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.theopus.entity.schedule.Building;
import com.theopus.entity.schedule.Faculty;

public class JdbcBuildingRepository implements Repository<Building> {

    private final NamedParameterJdbcTemplate template;
    private final String query;

    public JdbcBuildingRepository(DataSource dataSource, String queryAll) {
        template = new NamedParameterJdbcTemplate(dataSource);
        this.query = queryAll;
    }

    @Override
    public List<Building> all() {
        return template.query(query, (rs, rowNum) -> new Building(
                rs.getLong("ID"),
                rs.getString("NAME")));
    }

    @Override
    public Building get(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ID", id);
        try {
            return template.queryForObject(query
                    .concat(" AND KA1=:ID"), namedParameters, (rs, rowNum) -> new Building(
                    rs.getLong("ID"),
                    rs.getString("NAME")));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
