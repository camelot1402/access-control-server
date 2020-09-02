package io.piano.access_control_system.repository;

import io.piano.access_control_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
