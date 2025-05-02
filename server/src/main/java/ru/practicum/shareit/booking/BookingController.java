package ru.practicum.shareit.booking;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.BookingDtoOutput;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.service.BookingService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor

public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    BookingDto createBooking(@RequestHeader("X-Sharer-User-Id") Long userId, @RequestBody BookingDtoOutput bookingDto) {
        return bookingService.createBooking(userId, bookingDto);
    }

    @PatchMapping("/{id}")
    BookingDto updateBooking(@RequestHeader("X-Sharer-User-Id") Long ownerId, @PathVariable("id") Long bookingId,
                             @RequestParam Boolean approved) {
        return bookingService.updateBooking(ownerId, bookingId, approved);
    }

    @GetMapping("/{id}")
    BookingDto getBookingById(@RequestHeader("X-Sharer-User-Id") Long bookerId, @PathVariable("id") Long bookingId) {
        return bookingService.getBookingById(bookerId, bookingId);
    }

    @GetMapping
    List<BookingDto> getAllUserBookings(@RequestHeader("X-Sharer-User-Id") Long userId,
                                        @RequestParam(value = "state", defaultValue = "ALL") BookingState state) {
        return bookingService.getAllUserBookings(userId, state);
    }

    @GetMapping("/owner")
    List<BookingDto> getAllBookingByOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                          @RequestParam(value = "state", defaultValue = "ALL") BookingState state) {
        return bookingService.getAllBookingByOwner(ownerId, state);
    }
}
