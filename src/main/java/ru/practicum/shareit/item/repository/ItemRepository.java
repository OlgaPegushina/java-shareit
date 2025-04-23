package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(Long userId, Item item);

    Item update(Item item);

    Optional<Item> findById(Long itemId);

    List<Item> findAllItemsOwner(Long userId);

    List<Item> findItemsByNameOrDescription(String text);
}
