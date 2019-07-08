package com.theopus.schedule.backend.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.theopus.entity.schedule.Department;
import com.theopus.entity.schedule.Faculty;

public class JdbcDepartmentRepository implements DepartmentRepository {

    private final NamedParameterJdbcTemplate template;
    private final String query;

    public JdbcDepartmentRepository(DataSource dataSource, String queryAll) {
        template = new NamedParameterJdbcTemplate(dataSource);
        this.query = queryAll;
    }

    @Override
    public List<Department> all() {
        return template.query(query, (rs, rowNum) -> new Department(
                rs.getInt("ID"),
                rs.getString("FULL_NAME"),
                rs.getString("SHORT_NAME")));
    }

    @Override
    public Department get(Long id) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ID", id);
        try {
            return template.queryForObject(query
                    .concat(" AND K1=:ID"), namedParameters, (rs, rowNum) -> new Department(
                    rs.getInt("ID"),
                    rs.getString("FULL_NAME"),
                    rs.getString("SHORT_NAME")));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //K7 -- faculty
    @Override
    public List<Department> get(Faculty faculty) {
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("FID", faculty.getId());

        return template.query(query
                .concat(" AND K7 = :FID"), namedParameters, (rs, rowNum) -> new Department(
                rs.getInt("ID"),
                rs.getString("FULL_NAME"),
                rs.getString("SHORT_NAME")));
    }
}
