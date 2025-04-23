package ru.practicum.shareit.item.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static ItemDto mapToItemDto(Item item) {
        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .rentalCount(item.getRentalCount())
                .build();
    }

    public static Item mapToItem(ItemDto itemDto) {
        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .rentalCount(itemDto.getRentalCount())
                .build();
    }

    public static Item mapToItemNew(NewItemRequest requestItemDto) {
        return Item.builder()
                .name(requestItemDto.getName())
                .description(requestItemDto.getDescription())
                .available(requestItemDto.getAvailable())
                .build();
    }

    public static Item updateItemFields(Item item, UpdateItemRequest requestItemDto) {
        if (requestItemDto.hasName()) {
            item.setName(requestItemDto.getName());
        }
        if (requestItemDto.hasDescription()) {
            item.setDescription(requestItemDto.getDescription());

        }
        if (requestItemDto.hasAvailable()) {
            item.setAvailable(requestItemDto.getAvailable());
        }

        return item;
    }
}
