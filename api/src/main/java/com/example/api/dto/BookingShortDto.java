package com.example.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Короткая информация о бронировании")
public record BookingShortDto(@Schema(description = "Идентификатор бронирования") Long id,
                              @Schema(description = "Дата и время начала бронирования") LocalDateTime start,
                              @Schema(description = "Дата и время окончания бронирования") LocalDateTime end,
                              @Schema(description = "Идентификатор пользователя, который осуществляет бронирование") Long bookerId) {
}
