package com.example.server.dto;

import com.example.api.dto.ItemDto;
import com.example.api.dto.UserDto;
import com.example.server.repository.entity.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingDto {
    private final Long id;
    private final LocalDateTime start;
    private final LocalDateTime end;
    private final ItemDto item;
    private final UserDto booker;
    private final BookingStatus status;
}
