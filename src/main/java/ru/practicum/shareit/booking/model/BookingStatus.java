package ru.practicum.shareit.booking.model;

public enum BookingStatus {
    WAITING,
    APPROVED,
    REJECTED, //бронирование отклонено владельцем
    CANCELED //бронирование отменено создателем
}
