package com.example.api.dto;

import com.example.api.dto.enums.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Запрос на бронирование")
public record BookingDto(@Schema(description = "Идентификатор запроса") Long id,
                         @Schema(description = "Дата и время начала бронирования") LocalDateTime start,
                         @Schema(description = "Дата и время окончания бронирования") LocalDateTime end,
                         @Schema(description = "Предмет, который пользователь бронирует") ItemDto item,
                         @Schema(description = "Пользователь, который осуществляет бронирование") UserDto booker,
                         @Schema(description = "Статус бронирования") BookingStatus status) {
}
