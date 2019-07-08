package com.theopus.schedule.backend.repository;

import java.util.List;

import com.theopus.entity.schedule.Department;
import com.theopus.entity.schedule.Faculty;

public interface DepartmentRepository extends Repository<Department> {
    //K7 -- faculty
    List<Department> get(Faculty id);
}
