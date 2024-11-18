package com.example.server;

import com.example.api.dto.BookingShortDto;
import com.example.api.dto.enums.BookingStatus;
import com.example.server.repository.entity.Booking;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class BookingUtils {

    private static final Comparator<Booking> BY_START_ASCENDING = Comparator.comparing(Booking::getStart);
    private static final Comparator<Booking> BY_START_DESCENDING = BY_START_ASCENDING.reversed();

    public static BookingShortDto findLastBooking(List<Booking> bookings) {
        LocalDateTime now = LocalDateTime.now();

        return bookings.stream()
                .sorted(BY_START_DESCENDING)
                .filter(booking -> booking.getStatus() == BookingStatus.APPROVED && booking.getStart().isBefore(now))
                .findFirst()
                .map(booking -> new BookingShortDto(booking.getId(), booking.getStart(), booking.getEnd(), booking.getBooker().getId()))
                .orElse(null);
    }

    public static BookingShortDto findNextBooking(List<Booking> bookings) {
        LocalDateTime now = LocalDateTime.now();

        return bookings.stream()
                .sorted(BY_START_ASCENDING)
                .filter(booking -> booking.getStatus() == BookingStatus.APPROVED && booking.getStart().isAfter(now))
                .findFirst()
                .map(booking -> new BookingShortDto(booking.getId(), booking.getStart(), booking.getEnd(), booking.getBooker().getId()))
                .orElse(null);
    }
}
