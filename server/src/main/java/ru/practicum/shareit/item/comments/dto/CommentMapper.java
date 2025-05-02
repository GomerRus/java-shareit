package ru.practicum.shareit.item.comments.dto;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.item.comments.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;

@Component
public class CommentMapper {

    public static CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .itemId(comment.getItem().getId())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
    }

    public static Comment mapToComment(CommentDto commentDto, User author, Item item) {
        if (commentDto != null) {
            Comment comment = new Comment();
            comment.setId(commentDto.getId());
            comment.setText(commentDto.getText());
            comment.setAuthor(author);
            comment.setItem(item);
            comment.setCreated(LocalDateTime.now());
            return comment;
        }
        return null;
    }
}