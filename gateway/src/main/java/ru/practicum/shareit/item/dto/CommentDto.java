package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDto {

    private Long id;

    @NotBlank(message = "COMMENT Text не может быть пустым.")
    private String text;
    private LocalDateTime created;
}
