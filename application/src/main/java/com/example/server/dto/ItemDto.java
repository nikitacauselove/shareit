package com.example.server.dto;

import lombok.Data;

@Data
public class ItemDto {
    private final Long id;
    private final String name;
    private final String description;
    private final Boolean available;
    private final Long requestId;

    public Boolean isAvailable() {
        return this.available;
    }
}
