package ru.practicum.shareit.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"itemRequestId"})
public class ItemRequest {
    Long itemRequestId;
    String description;
    Long requestorId;
    LocalDate created;
}
