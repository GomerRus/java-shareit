package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.shareit.booking.validator.DateControl;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@DateControl
public class BookingDto {
    @NotNull(message = "ItemBooking ID cannot be NULL.")
    private Long itemId;

    @NotNull(message = "Booking DATE START cannot be NULL.")
    @Future(message = "Invalid booking date START. It should be provided as a future or present date.")
    private LocalDateTime start;

    @NotNull(message = "Booking DATE END cannot be NULL.")
    @Future(message = "Invalid date END. It should be provided as a future.")
    private LocalDateTime end;

}