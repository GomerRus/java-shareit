package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDto {

    @NotBlank(message = "DESCRIPTION ItemRequest = null or blank space ")
    @Size(max = 512, message = "Max length DESCRIPTION ItemRequest - 255 characters")
    private String description;

}
