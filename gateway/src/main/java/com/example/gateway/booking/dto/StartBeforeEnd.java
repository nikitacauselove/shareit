package com.example.gateway.booking.dto;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = StartBeforeEndValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface StartBeforeEnd {
    String message() default "Дата и время начала бронирования должна быть раньше, чем дата и время конца бронирования.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
