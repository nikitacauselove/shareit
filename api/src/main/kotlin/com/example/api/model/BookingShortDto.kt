package com.example.api.model

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Информация о запросе на бронирование")
data class BookingShortDto(

    @get:Schema(description = "Идентификатор запроса на бронирование")
    val id: Long,

    @get:Schema(description = "Дата и время начала бронирования")
    val start: LocalDateTime,

    @get:Schema(description = "Дата и время окончания бронирования")
    val end: LocalDateTime,

    @get:Schema(description = "Идентификатор пользователя")
    val bookerId: Long
)
