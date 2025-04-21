package ru.practicum.shareit.user.reposotory;

import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    User save(User user);

    Optional<User> findById(Long userId);

    User update(User user);

    void deleteById(Long userId);

    Optional<User> findByEmail(String email);
}
