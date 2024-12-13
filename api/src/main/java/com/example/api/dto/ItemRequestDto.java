package com.example.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Запрос на добавление предмета")
public record ItemRequestDto(@Schema(description = "Идентификатор запроса") Long id,
                             @Schema(description = "Текст запроса, содержащий описание требуемого предмета") @NotNull String description,
                             @Schema(description = "Идентификатор пользователя, создавшего запрос") Long requesterId,
                             @Schema(description = "Дата и время создания запроса") LocalDateTime created,
                             @Schema(description = "Список предметов, добавленных по этому запросу") List<ItemDto> items) {
}
