package com.example.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Информация о предмете и его бронировании")
public record ItemDtoWithBookings(@Schema(description = "Идентификатор предмета") Long id,
                                  @Schema(description = "Название предмета") String name,
                                  @Schema(description = "Описание предмета") String description,
                                  @Schema(description = "Доступен ли предмет для аренды") Boolean available,
                                  @Schema(description = "Короткая информация о последнем бронировании") BookingShortDto lastBooking,
                                  @Schema(description = "Короткая информация о следующем бронировании") BookingShortDto nextBooking,
                                  @Schema(description = "Список отзывов") List<CommentDto> comments) {
}
