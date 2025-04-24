package ru.practicum.shareit.item.repository;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.model.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemRepositoryInMemoryImpl implements ItemRepository {
    Map<Long, Item> items = new HashMap<>();

    @NonFinal
    Long globalItemId = 0L;

    @Override
    public Item save(Item item) {
        item.setId(generateId());
        items.put(item.getId(), item);
        return items.get(item.getId());
    }

    @Override
    public Item update(Item item) {
        Long itemId = item.getId();
        items.put(itemId, item);
        return items.get(itemId);
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        return Optional.ofNullable(items.get(itemId));
    }

    @Override
    public List<Item> findAllItemsOwner(Long userId) {
        return items.values().stream().filter(item -> item.getOwnerId().equals(userId)).toList();
    }

    @Override
    public List<Item> findItemsByNameOrDescription(String text) {
        String searchText = text.toUpperCase();
        return items.values().stream()
                .filter(item -> (item.getName().toUpperCase().contains(searchText)
                                 || item.getDescription().toUpperCase().contains(searchText))
                                && item.getAvailable())
                .toList();
    }

    private Long generateId() {
        return ++globalItemId;
    }
}
