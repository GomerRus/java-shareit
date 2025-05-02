package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private Long id;

    @NotBlank(message = "COMMENT Text не может быть пустым.")
    private String text;
    private LocalDateTime created;
}
