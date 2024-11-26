package com.example.server.mapper;

import com.example.api.dto.BookingCreationDto;
import com.example.api.dto.BookingDto;
import com.example.api.dto.ItemDto;
import com.example.api.dto.UserDto;
import com.example.api.dto.enums.BookingStatus;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    public Booking toBooking(BookingCreationDto bookingCreationDto, Item item, User booker) {
        return new Booking(null, bookingCreationDto.start(), bookingCreationDto.end(), item, booker, BookingStatus.WAITING);
    }

    public BookingDto toBookingDto(Booking booking) {
        ItemDto itemDto = itemMapper.toItemDto(booking.getItem());
        UserDto userDto = userMapper.toUserDto(booking.getBooker());

        return new BookingDto(booking.getId(), booking.getStart(), booking.getEnd(), itemDto, userDto, booking.getStatus());
    }

    public List<BookingDto> toBookingDto(List<Booking> bookings) {
        return bookings.stream()
                .map(this::toBookingDto)
                .collect(Collectors.toList());
    }
}
