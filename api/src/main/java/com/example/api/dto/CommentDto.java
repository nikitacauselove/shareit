package com.example.api.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CommentDto(Long id,
                         @NotBlank String text,
                         String authorName,
                         LocalDateTime created) {
}
