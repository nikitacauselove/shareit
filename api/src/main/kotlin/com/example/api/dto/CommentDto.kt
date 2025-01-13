package com.example.api.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import java.time.LocalDateTime

@Schema(description = "Информация об отзыве")
data class CommentDto(
    @get:Schema(description = "Идентификатор отзыва") val id: Long?,
    @get:Schema(description = "Текст отзыва") @get:NotBlank val text: String?,
    @get:Schema(description = "Имя автора отзыва") val authorName: String?,
    @get:Schema(description = "Дата и время создания отзыва") val created: LocalDateTime?
)
