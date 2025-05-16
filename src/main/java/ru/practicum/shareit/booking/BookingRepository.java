package ru.practicum.shareit.booking;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    //findAllByItemOwnerId
    //ALL
    List<Booking> findAllByItemOwnerIdOrderByStart(Long ownerId, Sort sortOrder);

    //STAT Wait, reject
    List<Booking> findAllByItemOwnerIdAndStatusOrderByStart(Long ownerId, BookingStatus status, Sort sortOrder);

    //CURRENT
    List<Booking> findAllByItemOwnerIdAndStartLessThanEqualAndEndGreaterThanEqualOrderByStart(Long ownerId,
                                                                                              LocalDateTime now1,
                                                                                              LocalDateTime now2,
                                                                                              Sort sortOrder);

    //FUTURE
    List<Booking> findAllByItemOwnerIdAndStartAfterOrderByStart(Long ownerId, LocalDateTime now, Sort sortOrder);

    //PAST
    List<Booking> findAllByItemOwnerIdAndEndBeforeOrderByStart(Long ownerId, LocalDateTime now, Sort sortOrder);

    //findAllByBookerId
    //ALL
    List<Booking> findAllByBookerIdOrderByStart(Long bookerId, Sort sortOrder);

    //WAITING
    List<Booking> findAllByBookerIdAndStatusOrderByStart(Long bookerId, BookingStatus status, Sort sortOrder);

    //REJECTED, CANCELED
    List<Booking> findAllByBookerIdAndStatusInOrderByStart(Long bookerId, List<BookingStatus> statuses, Sort sortOrder);

    //CURRENT
    List<Booking> findAllByBookerIdAndStartLessThanEqualAndEndGreaterThanEqualOrderByStart(Long bookerId,
                                                                                           LocalDateTime now1,
                                                                                           LocalDateTime now2,
                                                                                           Sort sortOrder);

    //FUTURE
    List<Booking> findAllByBookerIdAndStartAfterOrderByStart(Long bookerId, LocalDateTime now, Sort sortOrder);

    //PAST
    List<Booking> findAllByBookerIdAndEndBeforeOrderByStart(Long bookerId, LocalDateTime now, Sort sortOrder);

    //other
    List<Booking> findAllByItemId(Long itemId);
}
