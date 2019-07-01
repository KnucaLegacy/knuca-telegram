package com.theopus.schedule.backend.repository;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.theopus.entity.schedule.Group;

public class JdbcRepositoryGroups implements Repository<Group> {

    private static String queryAll;
    private final String queryById;

    private final NamedParameterJdbcTemplate template;

    public JdbcRepositoryGroups(DataSource dataSource, String groupQuery, String queryById) {
        template = new NamedParameterJdbcTemplate(dataSource);
        this.queryAll = groupQuery;
        this.queryById = queryById;
    }

    @Override
    public List<Group> all() {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("Y", 2018)
                .addValue("S", 1);
        return template.query(queryAll, namedParameters, getGroupRowMapper());
    }

    @Override
    public Group get(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ID", id)
                .addValue("Y", 2018)
                .addValue("S", 1);
        try {
            return template.queryForObject(queryById, namedParameters, getGroupRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private RowMapper<Group> getGroupRowMapper() {
        return (rs, rowNum) -> {
            Group group = new Group();
            group.setId(rs.getLong("ID"));
            String name = rs.getString("SNAME");
            if (name.isEmpty()) {
                name = rs.getString("NAME");
            }
            group.setName(name);
            return group;
        };
    }
}
