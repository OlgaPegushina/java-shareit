package ru.practicum.shareit.item.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateItemRequest {
    Long id;
    String name;
    String description;
    Boolean available;
    Long ownerId;
    Long requestId;

    public boolean hasName() {
        return ! (name == null || name.isBlank());
    }

    public boolean hasDescription() {
        return ! (description == null || description.isBlank());
    }

    public boolean hasAvailable() {
        return ! (available == null);
    }

    public boolean hasRequestId() {
        return ! (requestId == null  || requestId == 0);
    }
}
