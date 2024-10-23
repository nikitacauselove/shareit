package com.example.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record ItemDto(Long id,
                      @NotBlank(message = "Название предмета не может быть пустым.") String name,
                      @NotBlank(message = "Описание предмета не может быть пустым.") String description,
                      @NotNull(message = "Статус о том, доступна или нет вещь для аренды, не может быть пустым.") Boolean available,
                      Long requestId) {
}
