package com.example.gateway.booking.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class StartBeforeEndValidator implements ConstraintValidator<StartBeforeEnd, BookingCreationDto> {
    @Override
    public boolean isValid(BookingCreationDto bookingCreationDto, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime start = bookingCreationDto.getStart();
        LocalDateTime end = bookingCreationDto.getEnd();

        return start != null && end != null && start.isBefore(end);
    }
}
