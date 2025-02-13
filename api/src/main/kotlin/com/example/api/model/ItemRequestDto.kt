package com.example.api.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@Schema(description = "Запрос на добавление предмета")
data class ItemRequestDto(

    @get:Schema(description = "Идентификатор запроса на добавление предмета")
    val id: Long?,

    @get:Schema(description = "Текст запроса на добавление предмета")
    @get:NotNull
    val description: String?,

    @get:Schema(description = "Идентификатор пользователя")
    val requesterId: Long?,

    @get:Schema(description = "Дата и время создания запроса на добавление предмета")
    val created: LocalDateTime?,

    @get:Schema(description = "Список предметов, добавленных по этому запросу")
    val items: List<ItemDto>?
)
