package ru.practicum.shareit.booking.dto;

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
    private LocalDateTime start;
    private LocalDateTime end;
    private ItemDto item;
    private UserDto booker;
    private BookingStatus status;
}