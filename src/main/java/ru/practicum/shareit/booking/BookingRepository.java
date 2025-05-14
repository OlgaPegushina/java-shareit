package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    //ALL
    List<Booking> findAllByItemOwnerIdOrderByStartDesc(Long ownerId);

    //STAT Wait, reject
    List<Booking> findAllByItemOwnerIdAndStatusOrderByStartDesc(Long ownerId, BookingStatus status);

    //CURRENT
    List<Booking> findAllByItemOwnerIdAndStartLessThanEqualAndEndGreaterThanEqualOrderByStartDesc(Long ownerId,
                                                                                                  LocalDateTime now1,
                                                                                                  LocalDateTime now2);

    //FUTURE
    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStartDesc(Long ownerId, LocalDateTime now);

    //PAST
    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStartDesc(Long ownerId, LocalDateTime now);

    //******

    //ALL
    List<Booking> findAllByBookerIdOrderByStartDesc(Long bookerId);

    //WAITING
    List<Booking> findAllByBookerIdAndStatusOrderByStartDesc(Long bookerId, BookingStatus status);

    //REJECTED, CANCELED
    List<Booking> findAllByBookerIdAndStatusInOrderByStartDesc(Long bookerId, List<BookingStatus> statuses);

    //CURRENT
    List<Booking> findAllByBookerIdAndStartLessThanEqualAndEndGreaterThanEqualOrderByStartDesc(Long bookerId,
                                                                                               LocalDateTime now1,
                                                                                               LocalDateTime now2);

    //FUTURE
    List<Booking> findAllByBookerIdAndStartAfterOrderByStartDesc(Long bookerId, LocalDateTime now);

    //PAST
    List<Booking> findAllByBookerIdAndEndBeforeOrderByStartDesc(Long bookerId, LocalDateTime now);

    List<Booking> findAllByItemId(Long itemId); //Все бронирования для вещи
}
