package com.example.api.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record ItemRequestDto(Long id,
                             @NotNull String description,
                             Long requesterId,
                             LocalDateTime created,
                             List<ItemDto> items) {
}
