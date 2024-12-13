package com.example.server.service;

import com.example.api.dto.enums.BookingState;
import com.example.server.repository.entity.Booking;

import java.util.List;

public interface BookingService {

    Booking create(Booking booking);

    Booking approveOrReject(long bookingId, long ownerId, boolean approved);

    Booking findById(long bookingId, long userId);

    List<Booking> findAllByBookerId(long bookerId, BookingState state, int from, int size);

    List<Booking> findAllByOwnerId(long ownerId, BookingState state, int from, int size);
}
