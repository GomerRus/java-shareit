package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RequestDto {

    private Long id;
    private String requestorName;
    private LocalDateTime created;
    @NotBlank(message = "DESCRIPTION ItemRequest = null or blank space ")
    @Size(max = 512, message = "Max length DESCRIPTION ItemRequest - 255 characters")
    private String description;

}
