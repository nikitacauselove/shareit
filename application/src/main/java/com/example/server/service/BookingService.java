package com.example.server.service;

import com.example.api.dto.BookingCreateDto;
import com.example.api.dto.enums.BookingState;
import com.example.server.repository.entity.Booking;

import java.util.List;

public interface BookingService {

    Booking create(BookingCreateDto bookingCreateDto, Long bookerId);

    Booking approveOrReject(Long bookingId, Long ownerId, Boolean approved);

    Booking findById(Long bookingId, Long userId);

    List<Booking> findAllByBookerId(Long bookerId, BookingState state, Integer from, Integer size);

    List<Booking> findAllByOwnerId(Long ownerId, BookingState state, Integer from, Integer size);
}
