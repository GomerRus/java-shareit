package ru.practicum.shareit.booking.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.practicum.shareit.booking.dto.BookingDto;

public class DateValidator implements ConstraintValidator<DateControl, BookingDto> {

    @Override
    public boolean isValid(BookingDto bookingDto, ConstraintValidatorContext constraintValidatorContext) {
        return bookingDto.getStart().isBefore(bookingDto.getEnd());
    }

}