package com.example.api.dto;

import com.example.api.constraint.StartBeforeEnd;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "Запрос на создание бронирования")
@StartBeforeEnd
public record BookingCreateDto(@Schema(description = "Идентификатор предмета") Long itemId,
                               @Schema(description = "Дата и время начала бронирования") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @FutureOrPresent @NotNull LocalDateTime start,
                               @Schema(description = "Дата и время окончания бронирования") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @Future @NotNull LocalDateTime end) {
}
