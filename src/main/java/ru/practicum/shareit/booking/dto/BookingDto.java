package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

@Data
@Builder
public class BookingDto {
    private Long id;

    @NotNull(message = "Date START cannot be NULL.")
    @FutureOrPresent(message = "Invalid date START. It should be provided as a future or present date.")
    private LocalDateTime start;

    @NotNull(message = "Date END cannot be NULL.")
    @Future(message = "Invalid date END. It should be provided as a future.")
    private LocalDateTime end;

    private ItemDto item;
    private UserDto booker;
    private BookingStatus status;
}