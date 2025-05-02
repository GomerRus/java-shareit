package ru.practicum.shareit.booking.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dal.BookingRepository;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.dto.BookingMapper;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.BookingNotFoundException;
import ru.practicum.shareit.exception.ItemIsNotAvailableException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dal.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.dal.UserRepository;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImp implements BookingService {
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(String.format("USER not found with ID'%d'", userId)));
    }

    private User getUserValid(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ValidationException(String.format("USER not found with ID'%d'", userId)));

    }

    private Booking getBooking(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException(String.format("BOOKING not found with ID'%d'", bookingId)));
    }

    @Override
    public BookingDto createBooking(Long userId, BookingDtoOutput bookingDto) {
        if (bookingDto.getStart().isAfter(bookingDto.getEnd()) || bookingDto.getStart().equals(bookingDto.getEnd())) {
            throw new ItemIsNotAvailableException("Error in the time range. Problem is in the START time or END time.");
        }
        User user = getUser(userId);
        Long itemId = bookingDto.getItemId();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException(String.format("ITEM not found with ID'%d'",
                        itemId)));
        if (!item.getAvailable()) {
            throw new ValidationException(String.format("ITEM with ID'%d' not available for booking.", item.getId()));
        }

        Booking booking = BookingMapper.mapToBooking(bookingDto);
        booking.setItem(item);
        booking.setBooker(user);
        booking.setStatus(BookingStatus.WAITING);

        return BookingMapper.mapToBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto updateBooking(Long ownerId, Long bookingId, Boolean approved) {
        getUserValid(ownerId);
        Booking booking = getBooking(bookingId);
        if (!booking.getItem().getOwner().getId().equals(ownerId)) {
            throw new NotFoundException(String.format("USER with ID '%d' is not the owner of this booking.", ownerId));
        }
        if (booking.getStatus() == BookingStatus.APPROVED && approved) {
            throw new ItemIsNotAvailableException("You can't approved it a second time");
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        return BookingMapper.mapToBookingDto(bookingRepository.save(booking));
    }

    @Override
    public BookingDto getBookingById(Long bookerId, Long bookingId) {
        User user = getUserValid(bookerId);
        Booking booking = getBooking(bookingId);

        if (!booking.getBooker().getId().equals(user.getId()) && !booking.getItem().getOwner().getId().equals(user.getId())) {
            throw new BookingNotFoundException(String.format("User with ID'%d' is not the author of the booking or the owner.", bookerId));
        }
        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    public List<BookingDto> getAllUserBookings(Long userId, BookingState state) {
        getUserValid(userId);
        Sort sort = Sort.by("start").descending();

        if (state.equals(BookingState.CURRENT)) {
            return bookingRepository.findAllByBookerIdCurrentForDate(userId, LocalDateTime.now(), sort).stream()
                    .map(BookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals(BookingState.PAST)) {
            return bookingRepository.findAllByBookerIdAndEndIsBefore(userId, LocalDateTime.now(), sort).stream()
                    .map(BookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals(BookingState.FUTURE)) {
            return bookingRepository.findAllByBookerIdAndStartIsAfter(userId, LocalDateTime.now(), sort).stream()
                    .map(BookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals(BookingState.WAITING)) {
            return bookingRepository.findAllByBookerIdWhichWaiting(userId, sort).stream()
                    .map(BookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals(BookingState.REJECTED)) {
            return bookingRepository.findAllByBookerIdWhichRejected(userId, sort).stream()
                    .map(BookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        }
        return bookingRepository.findAllByBookerId(userId, sort).stream()
                .map(BookingMapper::mapToBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingDto> getAllBookingByOwner(Long ownerId, BookingState state) {
        getUser(ownerId);
        List<Item> items = itemRepository.findAllByOwnerId(ownerId);
        if (items.isEmpty()) {
            throw new NotFoundException(String.format("USER with ID '%d' has no ITEMS", ownerId));
        }
        Sort sort = Sort.by("start").descending();

        if (state.equals(BookingState.CURRENT)) {
            return bookingRepository.findAllByItemOwnerIdCurrentForDate(ownerId, LocalDateTime.now(), sort).stream()
                    .map(BookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals(BookingState.PAST)) {
            return bookingRepository.findAllByItemOwnerIdAndEndIsBefore(ownerId, LocalDateTime.now(), sort).stream()
                    .map(BookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals(BookingState.FUTURE)) {
            return bookingRepository.findAllByItemOwnerIdAndStartIsAfter(ownerId, LocalDateTime.now(), sort).stream()
                    .map(BookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals(BookingState.WAITING)) {
            return bookingRepository.findAllByItemOwnerIdWhichWaiting(ownerId, sort).stream()
                    .map(BookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        }
        if (state.equals(BookingState.REJECTED)) {
            return bookingRepository.findAllByItemOwnerIdWhichRejected(ownerId, sort).stream()
                    .map(BookingMapper::mapToBookingDto)
                    .collect(Collectors.toList());
        }
        return bookingRepository.findAllByItemOwnerId(ownerId, sort).stream()
                .map(BookingMapper::mapToBookingDto)
                .collect(Collectors.toList());
    }
}
