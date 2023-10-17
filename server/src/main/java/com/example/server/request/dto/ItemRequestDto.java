package com.example.server.request.dto;

import com.example.server.item.dto.ItemDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ItemRequestDto {
    private final Long id;
    private final String description;
    private final Long requesterId;
    private final LocalDateTime created;
    private final List<ItemDto> items;
}
