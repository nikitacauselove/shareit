package com.example.api.dto;

import com.example.api.constraint.StartBeforeEnd;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@StartBeforeEnd
public record BookingCreationDto(Long itemId,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @FutureOrPresent @NotNull LocalDateTime start,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Future @NotNull LocalDateTime end) {
}
