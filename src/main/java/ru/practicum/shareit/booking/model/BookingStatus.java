package ru.practicum.shareit.booking.model;

public enum BookingStatus {
    WAITING,
    APPROVED,

    //бронирование отклонено владельцем
    REJECTED,

    //бронирование отменено создателем
    CANCELED
}
