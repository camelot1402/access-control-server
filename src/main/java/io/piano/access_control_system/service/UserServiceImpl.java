package io.piano.access_control_system.service;

import io.piano.access_control_system.exception.DivisionException;
import io.piano.access_control_system.exception.RoomAccessException;
import io.piano.access_control_system.model.Room;
import io.piano.access_control_system.model.User;
import io.piano.access_control_system.repository.RoomRepository;
import io.piano.access_control_system.repository.UserRepository;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Log
@Data
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Value("${db.rooms}")
    private String rooms;
    @Value("${db.users}")
    private String users;

    @Override
    public String check(Long roomId, Boolean entrance, Long keyId) {
        checkDivision(roomId, keyId);

        Room room = roomRepository.findById(roomId).orElseThrow();
        User user = userRepository.findById(keyId).orElseThrow();

        if (user.getRoom() == null && entrance) {
            user.setRoom(room);
            userRepository.save(user);
            log.info("User with id: " + keyId + " in the room: " + roomId);
            return "User with id: " + keyId + " in the room: " + roomId;
        }
        if (room.equals(user.getRoom()) && !entrance) {
            user.setRoom(null);
            userRepository.save(user);
            log.info("User with id: " + keyId + " left the room: " + roomId);
            return "User with id: " + keyId + " left the room: " + roomId;
        }
        log.severe("User with id: " + keyId + " and entrance: " + entrance + " can't access to the room: " + roomId);
        throw new RoomAccessException();
    }

    private void checkDivision(Long roomId, Long keyId) {
        if (keyId % roomId != 0) {
            log.severe("User with id: " + keyId + " can't access to the room: " + roomId);
            log.severe("because: " + keyId + " % " + roomId + " = " + keyId % roomId);
            throw new DivisionException();
        }
    }

    @PostConstruct
    private void prepareDataBase() {
        int roomsCount = Integer.parseInt(rooms);
        int usersCount = Integer.parseInt(users);
        saveToDataBase(roomsCount, roomRepository, Room.class);
        saveToDataBase(usersCount, userRepository, User.class);
        log.info("Created " + roomsCount + " rooms and " + usersCount + " users in database");
    }

    @SneakyThrows
    private <T> void saveToDataBase(int count, JpaRepository repository, Class<T> clazz) {
        List<T> entityList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            entityList.add(clazz.getDeclaredConstructor().newInstance());
        }
        repository.saveAll(entityList);
    }
}
