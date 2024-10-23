package com.example.api.dto;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public record CommentDto(Long id,
                         @NotBlank(message = "Текст комментария не может быть пустым.") String text,
                         String authorName,
                         LocalDateTime created) {
}
