package ru.practicum.shareit.exception;

public class DuplicateException extends RuntimeException {
    public DuplicateException(String message) {
        super(message);
    }
}