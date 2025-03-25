package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank(message = "Email cannot be NULL .")
    @Email(message = "Email must contain the character: @")
    private String email;
}
