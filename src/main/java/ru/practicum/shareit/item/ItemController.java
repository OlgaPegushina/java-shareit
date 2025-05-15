package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemController {
    ItemService itemService;

    @GetMapping
    public List<ItemDto> findAllOwnerItems(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.findItemsByOwnerId(userId);
    }

    @GetMapping("/search")
    public List<ItemDto> findItemsByNameOrDescription(@RequestParam(value = "text", required = false) String text) {
        return itemService.findItemsByNameOrDescription(text);
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@RequestHeader("X-Sharer-User-Id") Long userId, @PathVariable("itemId") Long itemId) {
        return itemService.findById(userId, itemId);
    }

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @Valid @RequestBody NewItemDto itemDto) {
        return itemService.create(userId, itemDto);
    }

    @PatchMapping("{itemId}")
    public ItemDto update(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @PathVariable("itemId") Long itemId,
                          @Valid @RequestBody UpdateItemDto itemDto) {
        return itemService.update(userId, itemId, itemDto);
    }

    @PostMapping("{itemId}/comment")
    public CommentDto addComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                 @PathVariable("itemId") Long itemId,
                                 @Valid @RequestBody NewCommentDto commentDto
    ) {
        return itemService.addComment(userId, itemId, commentDto);
    }
}
