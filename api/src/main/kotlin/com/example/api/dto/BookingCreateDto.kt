package com.example.api.dto

import com.example.api.constraint.StartBeforeEnd
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.FutureOrPresent
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

@Schema(description = "Информация о запросе на бронирование")
@StartBeforeEnd
data class BookingCreateDto(
    @get:Schema(description = "Идентификатор предмета") val itemId: Long,
    @get:Schema(description = "Дата и время начала бронирования") @get:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @get:FutureOrPresent @get:NotNull val start: LocalDateTime?,
    @get:Schema(description = "Дата и время окончания бронирования") @get:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @get:Future @get:NotNull val end: LocalDateTime?
)
