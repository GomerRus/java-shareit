package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto createUser(@Valid @RequestBody User user) {
        log.info("POST / users: Create USER - {}", user.getName());
        return userService.createUser(user);
    }

    @GetMapping("/{userId}")
    public UserDto getUserById(@PathVariable("userId") Long userId) {
        log.info("GET /users: Get USER by ID: {}", userId);
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        log.info("GET /users: Get list the all USERS");
        return userService.getAllUsers();
    }

    @PatchMapping("/{userId}")
    public UserDto updateUser(@Valid @RequestBody UserDto user, @PathVariable("userId") Long userId) {
        log.info("PATH / users/{}: Update USER - {}", userId, user);
        return userService.updateUser(user, userId);
    }

    @DeleteMapping("/{userId}")
    public void removeUserById(@PathVariable("userId") Long userId) {
        log.info("DELETE / users: Delete USER by ID - {}", userId);
        userService.removeUserById(userId);
    }
}











