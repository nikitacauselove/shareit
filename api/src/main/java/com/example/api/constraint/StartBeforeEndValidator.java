package com.example.api.constraint;

import com.example.api.dto.BookingCreationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, BookingCreationDto> {

    public static final String MESSAGE = "дата и время начала бронирования должна быть раньше, чем дата и время окончания бронирования";

    @Override
    public boolean isValid(BookingCreationDto bookingCreationDto, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime start = bookingCreationDto.start();
        LocalDateTime end = bookingCreationDto.end();

        return start != null && end != null && start.isBefore(end);
    }
}
