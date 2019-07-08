package com.theopus.schedule.backend.repository;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.theopus.entity.schedule.Faculty;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.enums.YearOfStudy;

public class JdbcRepositoryGroups implements GroupRepository {

    private final String queryAll;
    private final String queryById;
    private final String queryByFacultyAndYos;

    private final NamedParameterJdbcTemplate template;

    public JdbcRepositoryGroups(DataSource dataSource, String groupQuery, String queryById, String queryByFacultyAndYos) {
        template = new NamedParameterJdbcTemplate(dataSource);
        this.queryAll = groupQuery;
        this.queryById = queryById;
        this.queryByFacultyAndYos = queryByFacultyAndYos;
    }

    @Override
    public List<Group> all() {
        SqlParameterSource namedParameters = getNamedParameters(new MapSqlParameterSource());
        return template.query(queryAll, namedParameters, getGroupRowMapper());
    }

    @Override
    public Group get(Long id) {
        SqlParameterSource namedParameters = getNamedParameters(new MapSqlParameterSource())
                .addValue("ID", id);
        try {
            return template.queryForObject(queryById, namedParameters, getGroupRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Group> get(Faculty faculty, YearOfStudy yos) {
        SqlParameterSource namedParameters = getNamedParameters(new MapSqlParameterSource())
                .addValue("F", faculty.getId())
                .addValue("YOS", yos.intVal());
        return template.query(queryByFacultyAndYos, namedParameters, getGroupRowMapper());
    }

    private MapSqlParameterSource getNamedParameters(MapSqlParameterSource param) {
        LocalDate now = LocalDate.now();
        int semester = now.getMonthValue() >= 8 ? 0 : 1;
        int year = now.getMonthValue() >= 8 ? now.getYear() : now.getYear() - 1;
        return param
                .addValue("Y", year)
                .addValue("S", semester);
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
