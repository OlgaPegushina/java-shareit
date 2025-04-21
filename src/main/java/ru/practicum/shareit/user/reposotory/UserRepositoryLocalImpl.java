package ru.practicum.shareit.user.reposotory;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.user.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRepositoryLocalImpl implements UserRepository {
    Map<Long, User> users = new HashMap<>();
    Map<String, User> usersByEmail = new HashMap<>();

    @NonFinal
    Long globalUserId = 0L;

    @Override
    public List<User> findAll() {
        return users.values().stream().toList();
    }

    @Override
    public User save(User user) {
        user.setId(generateId());
        users.put(user.getId(), user);
        usersByEmail.put(user.getEmail(), user);
        return users.get(user.getId());
    }

    @Override
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(users.get(userId));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(usersByEmail.get(email));
    }

    @Override
    public User update(User user) {
        Long userId = user.getId();
        User userToUpdate = users.get(userId);
        if (user.getName() != null && !user.getName().isBlank()) {
            userToUpdate.setName(user.getName());
        }
        if (user.getEmail() != null && !user.getEmail().isBlank()) {
            userToUpdate.setEmail(user.getEmail());
        }
        return users.get(userId);
    }

    @Override
    public void deleteById(Long userId) {
        users.remove(userId);
    }

    private Long generateId() {
        return ++globalUserId;
    }
}
