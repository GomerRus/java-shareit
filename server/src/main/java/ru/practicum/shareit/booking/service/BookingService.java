package ru.practicum.shareit.booking.service;

import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.List;

public interface BookingService {
    BookingDto createBooking(Long userId, BookingDtoOutput bookingDto);

    BookingDto updateBooking(Long ownerId, Long bookingId, Boolean approved);

    BookingDto getBookingById(Long bookerId, Long bookingId);

    List<BookingDto> getAllUserBookings(Long userId, BookingState state);

    List<BookingDto> getAllBookingByOwner(Long ownerId, BookingState state);
}
