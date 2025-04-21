package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewItemRequest {
    @NotBlank(message = "Название не может быть пустым")
    String name;

    @NotBlank(message = "Описание не может быть пустым")
    String description;

    @NotBlank(message = "Статус аренды не может быть пустым")
    Boolean available;

    @NotBlank(message = "ID владельца не может быть пустым")
    Long ownerId;

    Long requestId;
}
