package ru.practicum.shareit.item.comments.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommentDto {

    private Long id;
    private String text;
    private String authorName;
    private Long itemId;
    private LocalDateTime created;
}
