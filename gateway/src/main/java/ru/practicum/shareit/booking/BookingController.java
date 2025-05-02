package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.enums.BookingState;

@Slf4j
@Validated
@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor

public class BookingController {

    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> createBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @Valid @RequestBody BookingDto bookingDto) {
        log.info("Post /bookings: Create booking - {}.", bookingDto);
        return bookingClient.createBooking(userId, bookingDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateBooking(@RequestHeader("X-Sharer-User-Id") long userId,
                                                @PathVariable("id") Long bookingId, @RequestParam Boolean approved) {
        log.info("Path / bookings: Update booking: {}, {}.", userId, bookingId);
        return bookingClient.updateBooking(userId, bookingId, approved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookingById(@RequestHeader("X-Sharer-User-Id") long userId,
                                                 @PathVariable("id") Long bookingId) {
        log.info("GET /booking/{}: Get booking: Booker ID {}.", bookingId, userId);
        return bookingClient.getBookingById(userId, bookingId);
    }

    @GetMapping
    public ResponseEntity<Object> getAllUserBookings(@RequestHeader("X-Sharer-User-Id") long userId,
                                                     @RequestParam(value = "state", defaultValue = "ALL") BookingState state) {
        log.info("Get/ booking: Get all USER bookings: {}, {}.", userId, state);
        return bookingClient.getAllUserBookings(userId, state);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getAllBookingByOwner(@RequestHeader("X-Sharer-User-Id") long userId,
                                                       @RequestParam(value = "state", defaultValue = "ALL") BookingState state) {
        log.info("Get /bookings: Get all OWNER bookings: {}, {}.", userId, state);
        return bookingClient.getAllBookingByOwner(userId, state);
    }
}
