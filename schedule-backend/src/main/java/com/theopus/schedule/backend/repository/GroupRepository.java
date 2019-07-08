package com.theopus.schedule.backend.repository;

import java.util.List;

import com.theopus.entity.schedule.Faculty;
import com.theopus.entity.schedule.Group;
import com.theopus.entity.schedule.enums.YearOfStudy;

public interface GroupRepository extends Repository<Group> {
    List<Group> get(Faculty faculty, YearOfStudy yos);
}
