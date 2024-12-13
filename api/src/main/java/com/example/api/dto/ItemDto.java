package com.example.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Информация о предмете")
public record ItemDto(@Schema(description = "Идентификатор предмета") Long id,
                      @Schema(description = "Название предмета") @NotBlank String name,
                      @Schema(description = "Описание предмета") @NotBlank String description,
                      @Schema(description = "Доступен ли предмет для аренды") @NotNull Boolean available,
                      @Schema(description = "Ссылка на соответствующий запрос, если предмет был создан по запросу другого пользователя") Long requestId) {
}
