package com.example.server.mapper;

import com.example.api.dto.BookingCreateDto;
import com.example.api.dto.BookingDto;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ItemMapper.class, UserMapper.class})
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "WAITING")
    Booking toBooking(BookingCreateDto bookingCreateDto, Item item, User booker);

    BookingDto toBookingDto(Booking booking);

    List<BookingDto> toBookingDto(List<Booking> bookings);
}
