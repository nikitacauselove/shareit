package com.example.api.dto;

import com.example.api.constraint.StartBeforeEnd;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@StartBeforeEnd
public record BookingCreationDto(Long itemId,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                 @FutureOrPresent(message = "Дата и время начала бронирования должна быть в настоящем или будущем.")
                                 @NotNull(message = "Дата и время начала бронирования не может быть пустой.") LocalDateTime start,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                 @Future(message = "Дата и время конца бронирования должна быть в будущем.")
                                 @NotNull(message = "Дата и время конца бронирования не может быть пустой.") LocalDateTime end) {
}
