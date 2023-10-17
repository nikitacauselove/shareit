package com.example.gateway.request.dto;

import com.example.gateway.item.dto.ItemDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemRequestDto {
    private final Long id;

    @NotNull(message = "Описание запроса на добавление вещи не может быть пустым.")
    private final String description;
    private final Long requesterId;
    private final LocalDateTime created;
    private final List<ItemDto> items;
}
