package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.dal.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(User user) {
        if (getAllUsers().stream().anyMatch(user1 -> user1.getEmail().equals(user.getEmail()))) {
            throw new DuplicateException(String.format("This email address '{}' is already in exists.", user.getEmail()));
        }
        return UserMapper.mapToUserDto(userRepository.createUser(user));
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("USER not found."));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto user, Long userId) {
        if (userId == null) {
            throw new ValidationException("USER ID must be specified");
        }
        if (user.getEmail() != null) {
            boolean checkUserEmail = getAllUsers().stream().anyMatch(user1 -> user1.getEmail().equals(user.getEmail()));
            if (checkUserEmail) {
                throw new DuplicateException(String.format("This email address '{}' is already in exists.", user.getEmail()));
            }
        }
        User oldUser = userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("User not found."));
        user.setId(oldUser.getId());

        return UserMapper.mapToUserDto(userRepository.updateUser(user));
    }

    @Override
    public void removeUserById(Long userId) {
        userRepository.removeUserById(userId);
    }
}
