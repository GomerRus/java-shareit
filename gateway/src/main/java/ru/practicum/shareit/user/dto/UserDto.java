package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotEmpty(message = "USER NAME не может быть пустым")
    private String name;

    @NotEmpty(message = "USER EMAIL не может быть пустой")
    @Email(message = "USER EMAIL должна содержать символ @")
    private String email;
}