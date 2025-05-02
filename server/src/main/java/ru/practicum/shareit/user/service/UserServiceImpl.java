package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicateException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.user.dal.UserRepository;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.dto.UserMapper;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("USER not found with ID '%d'.", userId)));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (getAllUsers().stream().anyMatch(user1 -> user1.getEmail().equals(userDto.getEmail()))) {
            throw new DuplicateException(String.format("This email address '%s' is already in exists.", userDto.getEmail()));
        }
        User user = UserMapper.mapToUser(userDto);

        return UserMapper.mapToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto getUserById(Long userId) {
        User user = getUser(userId);
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        if (userId == null) {
            throw new ValidationException("USER ID must be specified");
        }
        User user = getUser(userId);
        if (userDto.getName() != null) {
            user.setName(userDto.getName());
        }
        if (userDto.getEmail() != null) {
            if (getAllUsers().stream()
                    .filter(user1 -> !user1.getId().equals(userDto.getId()))
                    .anyMatch(user1 -> user1.getEmail().equalsIgnoreCase(userDto.getEmail()))) {
                throw new DuplicateException(String.format("This email address '%s' is already in exists.", userDto.getEmail()));
            }
        }
        user.setEmail(userDto.getEmail());
        return UserMapper.mapToUserDto(userRepository.save(user));
    }

    @Override
    public void removeUserById(Long userId) {
        userRepository.delete(getUser(userId));
    }
}
