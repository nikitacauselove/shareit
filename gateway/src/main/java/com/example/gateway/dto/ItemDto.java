package com.example.gateway.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ItemDto {
    private final Long id;

    @NotBlank(message = "Название предмета не может быть пустым.")
    private final String name;

    @NotBlank(message = "Описание предмета не может быть пустым.")
    private final String description;

    @NotNull(message = "Статус о том, доступна или нет вещь для аренды, не может быть пустым.")
    private final Boolean available;
    private final Long requestId;

    public Boolean isAvailable() {
        return this.available;
    }
}
