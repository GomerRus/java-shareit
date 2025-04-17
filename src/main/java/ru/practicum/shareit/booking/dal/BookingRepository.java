package ru.practicum.shareit.booking.dal;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select b " +
            "from Booking b " +
            "where b.booker.id = ?1 " +
            "and ?2 between b.start and b.end")
    List<Booking> findAllByBookerIdCurrentForDate(Long userId, LocalDateTime now, Sort sort);

    List<Booking> findAllByBookerIdAndEndIsBefore(Long userId, LocalDateTime now, Sort sort);

    List<Booking> findAllByBookerIdAndStartIsAfter(Long userId, LocalDateTime now, Sort sort);

    @Query("select b " +
            "from Booking b " +
            " where b.booker.id = ?1 " +
            "and b.status = 'WAITING'")
    List<Booking> findAllByBookerIdWhichWaiting(Long userId, Sort sort);

    @Query("select b " +
            "from Booking b " +
            " where b.booker.id = ?1 " +
            "and b.status = 'REJECTED'")
    List<Booking> findAllByBookerIdWhichRejected(Long userId, Sort sort);

    List<Booking> findAllByBookerId(Long userId, Sort sort);

    @Query("select b " +
            "from Booking b " +
            "where b.item.owner.id = ?1 " +
            "and ?2 between b.start and b.end")
    List<Booking> findAllByItemOwnerIdCurrentForDate(Long userId, LocalDateTime now, Sort sort);

    List<Booking> findAllByItemOwnerIdAndEndIsBefore(Long userId, LocalDateTime now, Sort sort);

    List<Booking> findAllByItemOwnerIdAndStartIsAfter(Long userId, LocalDateTime now, Sort sort);

    @Query("select b " +
            "from Booking b " +
            " where b.item.owner.id = ?1 " +
            "and b.status = 'WAITING'")
    List<Booking> findAllByItemOwnerIdWhichWaiting(Long userId, Sort sort);

    @Query("select b " +
            "from Booking b " +
            " where b.item.owner.id = ?1 " +
            "and b.status = 'REJECTED'")
    List<Booking> findAllByItemOwnerIdWhichRejected(Long userId, Sort sort);

    List<Booking> findAllByItemOwnerId(Long userId, Sort sort);

    Booking findFirstByItemIdAndEndIsBeforeAndStatus(Long itemId, LocalDateTime now, BookingStatus bookingStatus, Sort prevSort);

    Booking findFirstByItemIdAndStartIsAfterAndStatus(Long itemId, LocalDateTime now, BookingStatus bookingStatus, Sort nextSort);

    @Query("select b " +
            "from Booking b " +
            "join b.booker " +
            "join b.item " +
            "where b.booker = ?1 and b.item = ?2 and b.start < ?3 " +
            "order by b.start desc")
    List<Booking> findAllByItemAndBookerPast(User booker, Item item, LocalDateTime now);

}
