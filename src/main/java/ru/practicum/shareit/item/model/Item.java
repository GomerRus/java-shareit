package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.BooleanFlag;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.user.model.User;

@Data
@Builder
public class Item {
    private Long id;

    @NotBlank(message = "NAME ITEM = null or blank space ")
    private String name;

    @NotBlank(message = "DESCRIPTION ITEM = null or blank space ")
    @Size(max = 255, message = "Max length DESCRIPTION ITEM - 255 characters")
    private String description;

    @BooleanFlag
    @NotNull(message = "AVAILABLE ITEM status cannot be NULL.")
    private Boolean available;

    private User owner;
    private ItemRequest request;
}
