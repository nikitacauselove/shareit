package com.example.api.dto;

import java.time.LocalDateTime;

public record BookingShortDto(Long id,
                              LocalDateTime start,
                              LocalDateTime end,
                              Long bookerId) {
}
