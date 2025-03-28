package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Data
@Builder
public class ItemRequestDto {

    private Long id;

    @NotBlank(message = "DESCRIPTION ItemRequest = null or blank space ")
    @Size(max = 255, message = "Max length DESCRIPTION ItemRequest - 255 characters")
    private String description;

    private User requester;
    private LocalDateTime created;
}
