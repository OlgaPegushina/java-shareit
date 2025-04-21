package ru.practicum.shareit.user.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.user.dto.NewUserRequest;
import ru.practicum.shareit.user.dto.UpdateUserRequest;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserMapper {
    public static UserDto mapToUserDto(User user) {
       return UserDto.builder()
               .id(user.getId())
               .name(user.getName())
               .email(user.getEmail())
               .build();
    }

    public static User updateUserFields(Long userId, UpdateUserRequest requestUserDto) {
         User userUpdate = User.builder()
                .id(userId)
                .build();
        if (requestUserDto.hasEmail()) {
            userUpdate.setEmail(requestUserDto.getEmail());
        }
        if (requestUserDto.hasName()) {
            userUpdate.setName(requestUserDto.getName());
        }
        return userUpdate;
    }

    public static User mapToUserNew(NewUserRequest requestUserDto) {
        return User.builder()
                .name(requestUserDto.getName())
                .email(requestUserDto.getEmail())
                .build();
    }
}
