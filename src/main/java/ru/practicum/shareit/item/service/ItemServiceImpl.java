package ru.practicum.shareit.item.service;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.ForbiddenExeption;
import ru.practicum.shareit.exception.InternalServerException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;

import ru.practicum.shareit.item.mapper.CommentMapper;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.service.UserService;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static ru.practicum.shareit.item.mapper.ItemMapper.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;
    UserService userService;
    BookingRepository bookingRepository;
    UserRepository userRepository;
    CommentRepository commentRepository;

    @Override
    public ItemDto create(Long userId, NewItemDto itemDto) {
        User owner = userService.validateUserExist(userId);
        Item item = mapToNewItem(itemDto);
        item.setOwner(owner);
        return mapToItemDto(itemRepository.save(item));
    }

    @Override
    public ItemDto findById(Long userId, Long itemId) {
        Item item = validateItemExist(itemId);
        User user = userService.validateUserExist(userId);

        ItemDto itemDto = ItemMapper.mapToItemDto(item);

        loadDetails(itemDto);

        return itemDto;
    }

    @Override
    public ItemDto update(Long userId, Long itemId, UpdateItemDto itemDto) {
        userService.validateUserExist(userId);
        Item item = validateItemExist(itemId);
        if (!item.getOwner().getId().equals(userId)) {
            throw new ValidationException("Предмет аренды не принадлежит данному пользователю");
        }
        updateItemFields(item, itemDto);
        item = itemRepository.save(item);
        return mapToItemDto(item);
    }

    @Override
    public List<ItemDto> findItemsByOwnerId(Long ownerId) {
        userService.validateUserExist(ownerId);

        List<ItemDto> itemDtos = itemRepository.findByOwnerIdOrderByIdAsc(ownerId).stream()
                .map(ItemMapper::mapToItemDto)
                .toList();

        itemDtos.forEach(this::loadDetails);

        return itemDtos;
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
    public CommentDto addComment(Long userId, Long itemId, NewCommentDto commentDto) {
        Item item = validateItemExist(itemId);

        Comment comment = CommentMapper.mapToComment(commentDto);
        if (validateItemAndCommentAuthorAndDate(userId, itemId)) {
            comment.setItem(item);
            comment.setAuthor(userRepository.findById(userId).get());
            comment.setCreated(LocalDateTime.now());
            return CommentMapper.mapToCommentDto(commentRepository.save(comment));
        }
        throw new InternalServerException("Невозможно добавить отзыв. Проверьте данные бронирования");
    }

    private Item validateItemExist(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(String.format("Предмет аренды с id %d не найден.", itemId)));
    }

    private boolean validateItemAndCommentAuthorAndDate(Long userId, Long itemId) {
        validateItemExist(itemId);
        List<Booking> bookings = bookingRepository.findAllByItemId(itemId);

        if (bookings.isEmpty()) {
            throw new ValidationException("Для данной вещи ещё не было бронирований! Вы не можете оставить комментарий!");
        }

        Booking booking = bookings.stream()
                .filter(booking1 -> booking1.getBooker().getId().equals(userId))
                .findFirst()
                .orElseThrow(() -> new ForbiddenExeption("Пользователь не имеет права оставлять комментарий, " +
                                                         "так как не был арендатором!"));

        if (!(booking.getStatus() == BookingStatus.APPROVED)) {
            throw new ForbiddenExeption("Пользователь не имеет подтвержденного бронирования!");
        }

        if (!booking.getEnd().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Пользователь не имеет завершенного бронирования!");
        }

        return true;
    }

    @Transactional
    private void loadDetails(ItemDto itemDto) {
        List<Comment> comments = commentRepository.findAllByItemId(itemDto.getId());
        itemDto.setComments(comments.stream()
                .map(CommentMapper::mapToCommentDto)
                .toList());

        List<Booking> bookings = bookingRepository.findAllByItemOwnerIdOrderByStartDesc(itemDto.getId());

        if (!bookings.isEmpty()) {
            Booking nextBooking = bookings.get(0);
            itemDto.setNextBooking(BookingMapper.mapToBookingDto(nextBooking));

            if (bookings.size() > 1) {
                Booking lastBooking = bookings.get(1);
                itemDto.setLastBooking(BookingMapper.mapToBookingDto(lastBooking));
            } else {
                itemDto.setLastBooking(BookingMapper.mapToBookingDto(nextBooking));
            }
        }
    }
}
