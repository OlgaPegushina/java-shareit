package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto create(NewUserRequest userDto);

    UserDto findById(Long userId);

    UserDto update(Long userId, UpdateUserRequest userDto);

    void deleteById(Long userId);

    User validateUserExist(Long userId);
}
