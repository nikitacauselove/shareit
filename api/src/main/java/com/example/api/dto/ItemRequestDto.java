package com.example.api.dto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record ItemRequestDto(Long id,
                             @NotNull(message = "Описание запроса на добавление вещи не может быть пустым.") String description,
                             Long requesterId,
                             LocalDateTime created,
                             List<ItemDto> items) {
}
