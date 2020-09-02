package io.piano.access_control_system.repository;

import io.piano.access_control_system.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
