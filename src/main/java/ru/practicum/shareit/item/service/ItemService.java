package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.model.Item;

import java.util.List;


public interface ItemService {
    ItemDto create(Long userId, NewItemRequest itemDto);

    ItemDto findById(Long itemId);

    ItemDto update(Long userId, Long itemId, UpdateItemRequest itemDto);

    List<ItemDto> findAllItemsOwner(Long userId);

    List<ItemDto> findItemsByNameOrDescription(String text);

    Item validateItemExist(Long itemId);
}
