package com.example.api.dto

import com.example.api.dto.enums.BookingStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Информация о запросе на бронирование")
data class BookingDto(
    @get:Schema(description = "Идентификатор запроса на бронирование") val id: Long,
    @get:Schema(description = "Дата и время начала бронирования") val start: LocalDateTime,
    @get:Schema(description = "Дата и время окончания бронирования") val end: LocalDateTime,
    @get:Schema(description = "Предмет, который пользователь бронирует") val item: ItemDto,
    @get:Schema(description = "Пользователь, который осуществляет бронирование") val booker: UserDto,
    @get:Schema(description = "Статус бронирования") val status: BookingStatus
)
