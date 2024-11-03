package com.example.server.mapper;

import com.example.api.dto.BookingCreationDto;
import com.example.api.dto.ItemDto;
import com.example.api.dto.UserDto;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.BookingStatus;
import com.example.server.dto.BookingDto;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookingMapper {
    public Booking toBooking(BookingCreationDto bookingCreationDto, Item item, User booker) {
        return new Booking(null, bookingCreationDto.start(), bookingCreationDto.end(), item, booker, BookingStatus.WAITING);
    }

    public BookingDto toBookingDto(Booking booking) {
        ItemDto itemDto = ItemMapper.toItemDto(booking.getItem());
        UserDto userDto = UserMapper.toUserDto(booking.getBooker());

        return new BookingDto(booking.getId(), booking.getStart(), booking.getEnd(), itemDto, userDto, booking.getStatus());
    }

    public List<BookingDto> toBookingDto(List<Booking> bookings) {
        return bookings.stream()
                .map(BookingMapper::toBookingDto)
                .collect(Collectors.toList());
    }
}
