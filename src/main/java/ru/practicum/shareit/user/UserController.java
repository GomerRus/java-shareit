package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.transfer.Create;
import ru.practicum.shareit.transfer.Update;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserDto createUser(@Validated(Create.class) @RequestBody UserDto userDto) {
        log.info("POST / users: Create USER - {}.", userDto.getName());
        return userService.createUser(userDto);
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
    public UserDto updateUser(@Validated(Update.class) @RequestBody UserDto userDto, @PathVariable("userId") Long userId) {
        log.info("PATH / users/{}: Update USER - {}", userId, userDto);
        return userService.updateUser(userDto, userId);
    }

    @DeleteMapping("/{userId}")
    public void removeUserById(@PathVariable("userId") Long userId) {
        log.info("DELETE / users: Delete USER by ID - {}", userId);
        userService.removeUserById(userId);
    }
}











