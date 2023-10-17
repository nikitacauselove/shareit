package com.example.server.booking.dto;

import com.example.server.booking.model.BookingStatus;
import com.example.server.item.dto.ItemDto;
import com.example.server.user.dto.UserDto;
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
