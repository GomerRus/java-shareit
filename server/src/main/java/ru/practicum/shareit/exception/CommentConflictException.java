package ru.practicum.shareit.exception;

public class CommentConflictException extends RuntimeException {
    public CommentConflictException(String message) {
        super(message);
    }
}
