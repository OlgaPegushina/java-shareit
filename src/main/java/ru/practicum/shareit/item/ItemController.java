package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.NewItemRequest;
import ru.practicum.shareit.item.dto.UpdateItemRequest;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemController {
    ItemService itemService;

    @GetMapping
    public List<ItemDto> findAllItemsOwner(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.findAllItemsOwner(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> findItemsByNameOrDescription(@RequestParam(value = "text", required = false) String text) {
        return itemService.findItemsByNameOrDescription(text);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable("itemId") Long itemId) {
        return itemService.findById(itemId);
    }

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long userId, @Valid @RequestBody NewItemRequest itemDto) {
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("itemId") Long itemId,
                          @Valid @RequestBody UpdateItemRequest itemDto) {
        return itemService.update(userId, itemId, itemDto);
    }
}
