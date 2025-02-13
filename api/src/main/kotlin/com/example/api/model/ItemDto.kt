package com.example.api.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "Информация о предмете")
data class ItemDto(

    @get:Schema(description = "Идентификатор предмета")
    val id: Long?,

    @get:Schema(description = "Название предмета")
    @get:NotBlank
    val name: String?,

    @get:Schema(description = "Описание предмета")
    @get:NotBlank
    val description: String?,

    @get:Schema(description = "Доступен ли предмет для аренды")
    @get:NotNull
    val available: Boolean?,

    @get:Schema(description = "Идентификатор запроса на добавление предмета")
    val requestId: Long?
)
