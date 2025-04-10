package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.transfer.Create;
import ru.practicum.shareit.transfer.Update;


@Data
@Builder
public class UserDto {

    private Long id;

    @NotBlank(groups = {Create.class}, message = "NAME не может быть пустым")
    private String name;

    @NotBlank(groups = {Create.class}, message = "EMAIL не может быть пустой")
    @Email(groups = {Create.class, Update.class}, message = "EMAIL должна содержать символ @")
    private String email;
}