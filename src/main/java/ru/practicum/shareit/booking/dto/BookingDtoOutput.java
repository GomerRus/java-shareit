package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingDtoOutput {

    @NotNull(message = "ITEM ID cannot be NULL.")
    private Long itemId;

    @NotNull(message = "Date START cannot be NULL.")
    @FutureOrPresent(message = "Invalid date START. It should be provided as a future or present date.")
    private LocalDateTime start;

    @NotNull(message = "Date END cannot be NULL.")
    @Future(message = "Invalid date END. It should be provided as a future.")
    private LocalDateTime end;

}
