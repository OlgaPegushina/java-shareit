package ru.practicum.shareit.item.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.Collections;
import java.util.List;

import static ru.practicum.shareit.item.mapper.ItemMapper.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;
    UserService userService;

    @Override
    public ItemDto create(Long userId, NewItemRequest itemDto) {
        userService.validateUserExist(userId);
        Item item = mapToItemNew(itemDto);
        return mapToItemDto(itemRepository.save(userId, item));
    }

    @Override
    public ItemDto findById(Long itemId) {
        return mapToItemDto(validateItemExist(itemId));
    }

    @Override
    public ItemDto update(Long userId, Long itemId, UpdateItemRequest itemDto) {
        userService.validateUserExist(userId);
        Item item = validateItemExist(itemId);
        if (!item.getOwnerId().equals(userId)) {
            throw new ValidationException("Предмет аренды не принадлежит данному пользователю");
        }
        updateItemFields(item, itemDto);
        itemRepository.update(item);
        return mapToItemDto(item);
    }

    @Override
    public List<ItemDto> findAllItemsOwner(Long userId) {
        userService.validateUserExist(userId);

        return itemRepository.findAllItemsOwner(userId).stream()
                .map(ItemMapper::mapToItemDto)
                .toList();
    }

    @Override
    public List<ItemDto> findItemsByNameOrDescription(String text) {
        if (text == null || text.isEmpty() || text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.findItemsByNameOrDescription(text).stream()
                .map(ItemMapper::mapToItemDto)
                .toList();
    }

    @Override
    public Item validateItemExist(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(String.format("Предмет аренды с id %d не найден.", itemId)));
    }
}
