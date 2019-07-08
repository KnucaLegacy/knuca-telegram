package com.theopus.schedule.backend.repository;

import java.util.List;

import com.theopus.entity.schedule.Department;
import com.theopus.entity.schedule.Faculty;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.Teacher;
import com.theopus.entity.schedule.enums.YearOfStudy;

public interface TeacherRepository extends Repository<Teacher> {
    //    PD4 - department
    List<Teacher> get(Department dept);
}
