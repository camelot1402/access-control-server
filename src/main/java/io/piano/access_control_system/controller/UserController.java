package io.piano.access_control_system.controller;

import io.piano.access_control_system.exception.DivisionException;
import io.piano.access_control_system.exception.RoomAccessException;
import io.piano.access_control_system.service.UserService;
import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@Log
@Data
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/check")
    @ResponseBody
    public String check(@RequestParam Long roomId, @RequestParam Boolean entrance, @RequestParam Long keyId) {
        log.info("Checking parameters: roomId: " + roomId + " entrance: " + entrance + " keyId: " + keyId);
        try {
            return userService.check(roomId, entrance, keyId);
        } catch (DivisionException | RoomAccessException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
