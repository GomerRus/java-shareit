package ru.practicum.shareit.user.dal;

import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User createUser(User user);

    Optional<User> getUserById(Long userId);

    List<User> getAllUsers();

    User updateUser(UserDto userDto, Long userId);

    void removeUserById(Long userId);
}
