package com.example.api.dto;

import com.example.api.dto.enums.BookingStatus;

import java.time.LocalDateTime;

public record BookingDto(Long id,
                         LocalDateTime start,
                         LocalDateTime end,
                         ItemDto item,
                         UserDto booker,
                         BookingStatus status) {
}
