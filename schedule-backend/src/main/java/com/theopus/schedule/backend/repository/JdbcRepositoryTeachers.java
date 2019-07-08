package com.theopus.schedule.backend.repository;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.theopus.entity.schedule.Department;
import com.theopus.entity.schedule.Teacher;

public class JdbcRepositoryTeachers implements TeacherRepository {

    private final JdbcTemplate template;
    private final String query;
    private final String queryById;

    public JdbcRepositoryTeachers(DataSource dataSource, String query, String queryById) {
        template = new JdbcTemplate(dataSource);
        this.query = query;
        this.queryById = queryById;
    }

    @Override
    public List<Teacher> all() {
        return template.query(query, getTeacherRowMapper());
    }

    private RowMapper<Teacher> getTeacherRowMapper() {
        return (rs, rowNum) -> {
            Teacher teacher = new Teacher();
            teacher.setId(rs.getLong("ID"));
            teacher.setName(rs.getString("FULL_NAME"));
            return teacher;
        };
    }


    @Override
    public Teacher get(Long id) {
        try {
            return template.queryForObject(queryById, new Object[]{id}, getTeacherRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    //    PD4 - department
    @Override
    public List<Teacher> get(Department dept) {
        return template.query(query
                .concat(" AND PD4 = ?"), new Object[]{dept.getId()}, getTeacherRowMapper());

    }
}
