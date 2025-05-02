package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private Long id;

    @NotBlank(message = "USER NAME не может быть пустым")
    private String name;

    @NotBlank(message = "USER EMAIL не может быть пустой")
    @Email(message = "USER EMAIL должна содержать символ @")
    private String email;
}