package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewItemDto {
    @NotBlank(message = "Название не может быть пустым")
    String name;

    @NotBlank(message = "Описание не может быть пустым")
    String description;

    @NotNull(message = "Отсутствует обязательный параметр \"Статус аренды\"")
    Boolean available;
}
