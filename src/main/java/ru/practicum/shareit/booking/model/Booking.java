package ru.practicum.shareit.booking.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/**
 * TODO Sprint add-bookings.
 */
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(of = {"bookingId"})
public class Booking {
    Long bookingId;
    LocalDate start;
    LocalDate end;
    Long itemId;
    Long bookerId;
    BookingStatus status;
}
