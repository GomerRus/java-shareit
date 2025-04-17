package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.transfer.Create;

@Data
@Builder
public class ItemDto {

    private Long id;

    @NotBlank(groups = {Create.class}, message = "NAME не может быть пустым")
    private String name;

    @NotBlank(groups = {Create.class}, message = "DESCRIPTION не может быть пустым")
    @Size(max = 255, message = "Max length DESCRIPTION ITEM - 255 characters")
    private String description;

    @NotNull(groups = {Create.class}, message = "AVAILABLE не может быть null")
    private Boolean available;
}
