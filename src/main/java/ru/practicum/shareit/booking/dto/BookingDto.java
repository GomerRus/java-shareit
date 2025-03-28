package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.booking.model.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDate;

@Data
@Builder
public class BookingDto {
    private Long id;

    @NotNull(message = "Date START cannot be NULL.")
    @FutureOrPresent(message = "Invalid date START. It should be provided as a future or present date.")
    private LocalDate start;

    @NotNull(message = "Date END cannot be NULL.")
    @Future(message = "Invalid date END. It should be provided as a future.")
    private LocalDate end;

    private Item item;
    private User booker;
    private Status status;
}