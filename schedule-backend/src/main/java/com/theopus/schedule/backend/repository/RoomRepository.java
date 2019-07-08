package com.theopus.schedule.backend.repository;

import java.util.List;

import com.theopus.entity.schedule.Building;
import com.theopus.entity.schedule.Room;

public interface RoomRepository extends Repository<Room> {
    List<Room> get(Building building);
}
