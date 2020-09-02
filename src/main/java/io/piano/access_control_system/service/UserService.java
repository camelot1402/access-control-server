package io.piano.access_control_system.service;

public interface UserService {
    String check(Long roomId, Boolean entrance, Long keyId);
}
