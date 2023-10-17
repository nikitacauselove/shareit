package com.example.server.booking.dto;

import com.example.server.booking.model.Booking;
import com.example.server.booking.model.BookingStatus;
import com.example.server.item.dto.ItemDto;
import com.example.server.item.dto.ItemMapper;
import com.example.server.item.model.Item;
import com.example.server.user.dto.UserDto;
import com.example.server.user.dto.UserMapper;
import com.example.server.user.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookingMapper {
    public Booking toBooking(BookingCreationDto bookingCreationDto, Item item, User booker) {
        return new Booking(null, bookingCreationDto.getStart(), bookingCreationDto.getEnd(), item, booker, BookingStatus.WAITING);
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
