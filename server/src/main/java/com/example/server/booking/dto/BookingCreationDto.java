package com.example.server.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingCreationDto {
    private final Long itemId;
    private final LocalDateTime start;
    private final LocalDateTime end;
}
