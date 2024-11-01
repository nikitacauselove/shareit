package com.example.server.dto;

import com.example.api.dto.CommentDto;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Data
public class ItemDtoWithBookings {
    private final Long id;
    private final String name;
    private final String description;
    private final Boolean available;
    private final BookingDto lastBooking;
    private final BookingDto nextBooking;
    private final List<CommentDto> comments;

    private static final Comparator<Booking> BY_START_ASCENDING = Comparator.comparing(Booking::getStart);
    private static final Comparator<Booking> BY_START_DESCENDING = BY_START_ASCENDING.reversed();

    @Data
    public static class BookingDto {
        private final Long id;
        private final LocalDateTime start;
        private final LocalDateTime end;
        private final Long bookerId;
    }

    public static BookingDto findLastBooking(List<Booking> bookings) {
        LocalDateTime now = LocalDateTime.now();

        return bookings.stream()
                .sorted(BY_START_DESCENDING)
                .filter(booking -> booking.getStatus() == BookingStatus.APPROVED && booking.getStart().isBefore(now))
                .findFirst()
                .map(booking -> new BookingDto(booking.getId(), booking.getStart(), booking.getEnd(), booking.getBooker().getId()))
                .orElse(null);
    }

    public static BookingDto findNextBooking(List<Booking> bookings) {
        LocalDateTime now = LocalDateTime.now();

        return bookings.stream()
                .sorted(BY_START_ASCENDING)
                .filter(booking -> booking.getStatus() == BookingStatus.APPROVED && booking.getStart().isAfter(now))
                .findFirst()
                .map(booking -> new BookingDto(booking.getId(), booking.getStart(), booking.getEnd(), booking.getBooker().getId()))
                .orElse(null);
    }
}
