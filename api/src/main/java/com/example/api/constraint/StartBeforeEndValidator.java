package com.example.api.constraint;

import com.example.api.dto.BookingCreateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, BookingCreateDto> {

    public static final String MESSAGE = "дата и время начала бронирования должны быть раньше, чем дата и время окончания бронирования";

    @Override
    public boolean isValid(BookingCreateDto bookingCreateDto, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime start = bookingCreateDto.start();
        LocalDateTime end = bookingCreateDto.end();

        return start != null && end != null && start.isBefore(end);
    }
}
