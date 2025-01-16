package com.example.api.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Schema(description = "Запрос на добавление предмета")
data class ItemRequestDto(
    @get:Schema(description = "Идентификатор запроса") val id: Long?,
    @get:Schema(description = "Текст запроса, содержащий описание требуемого предмета") @get:NotNull val description: String?,
    @get:Schema(description = "Идентификатор пользователя, создавшего запрос") val requesterId: Long?,
    @get:Schema(description = "Дата и время создания запроса") val created: LocalDateTime?,
    @get:Schema(description = "Список предметов, добавленных по этому запросу") val items: List<ItemDto?>?
)
