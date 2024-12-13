package com.example.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Schema(description = "Информация об отзыве")
public record CommentDto(@Schema(description = "Идентификатор отзыва") Long id,
                         @Schema(description = "Текст отзыва") @NotBlank String text,
                         @Schema(description = "Имя автора отзыва") String authorName,
                         @Schema(description = "Дата и время создания отзыва") LocalDateTime created) {
}
