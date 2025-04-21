package ru.practicum.shareit.user.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.DuplicatedDataException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.reposotory.UserRepository;

import java.util.List;
import java.util.Optional;

import static ru.practicum.shareit.user.mapper.UserMapper.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

    @Override
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::mapToUserDto)
                .toList();
    }

    @Override
    public UserDto create(NewUserRequest userDto) {
        validateEmailExist(userDto.getEmail());
        User user = mapToUserNew(userDto);
        return mapToUserDto(userRepository.save(user));
    }

    @Override
    public UserDto findById(Long userId) {
        return mapToUserDto(validateUserExist(userId));
    }

    @Override
    public UserDto update(Long userId, UpdateUserRequest userDto) {
        validateUserExist(userId);
        validateEmailExist(userDto.getEmail());
        User updatedUser = userRepository.findById(userId)
                .map(user -> updateUserFields(userId, userDto))
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден.", userId)));
        updatedUser = userRepository.update(updatedUser);
        return mapToUserDto(updatedUser);

    }

    @Override
    public void deleteById(Long userId) {
        validateUserExist(userId);
        userRepository.deleteById(userId);
    }

    private User validateUserExist(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь с id %d не найден.", userId)));
    }

    private void validateEmailExist(String email) {
        Optional<User> alreadyExistUser = userRepository.findByEmail(email);
        if (alreadyExistUser.isPresent()) {
            throw new DuplicatedDataException(String.format("Email - %s уже используется", email));
        }
    }
}
