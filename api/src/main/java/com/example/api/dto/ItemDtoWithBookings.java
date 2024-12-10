package com.example.api.dto;

import java.util.List;

public record ItemDtoWithBookings(Long id,
                                  String name,
                                  String description,
                                  Boolean available,
                                  BookingShortDto lastBooking,
                                  BookingShortDto nextBooking,
                                  List<CommentDto> comments) {
}
