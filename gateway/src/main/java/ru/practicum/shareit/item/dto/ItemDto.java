package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {

    private Long id;
    private Long requestId;

    @NotEmpty(message = "ITEM NAME не может быть пустым")
    private String name;

    @NotBlank(message = "ITEM DESCRIPTION не может быть пустым")
    @Size(max = 255, message = "Max length DESCRIPTION ITEM - 255 characters")
    private String description;

    @NotNull(message = "ITEM AVAILABLE не может быть null")
    private Boolean available;


}
