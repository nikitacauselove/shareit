package com.example.api.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Информация о предмете и его бронировании")
data class ItemDtoWithBookings(
    @get:Schema(description = "Идентификатор предмета") val id: Long,
    @get:Schema(description = "Название предмета") val name: String,
    @get:Schema(description = "Описание предмета") val description: String,
    @get:Schema(description = "Доступен ли предмет для аренды") val available: Boolean,
    @get:Schema(description = "Короткая информация о последнем бронировании") val lastBooking: BookingShortDto?,
    @get:Schema(description = "Короткая информация о следующем бронировании") val nextBooking: BookingShortDto?,
    @get:Schema(description = "Список отзывов") val comments: List<CommentDto?>?
)
