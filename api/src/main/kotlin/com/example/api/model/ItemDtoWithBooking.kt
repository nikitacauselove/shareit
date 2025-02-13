package com.example.api.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Информация о предмете")
data class ItemDtoWithBooking(

    @get:Schema(description = "Идентификатор предмета")
    val id: Long,

    @get:Schema(description = "Название предмета")
    val name: String,

    @get:Schema(description = "Описание предмета")
    val description: String,

    @get:Schema(description = "Доступен ли предмет для аренды")
    val available: Boolean,

    @get:Schema(description = "Информация о запросе на бронирование")
    val lastBooking: BookingShortDto?,

    @get:Schema(description = "Информация о запросе на бронирование")
    val nextBooking: BookingShortDto?,

    @get:Schema(description = "Список отзывов")
    val comments: List<CommentDto>?
)
