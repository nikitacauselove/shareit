package com.example.server.mapper;

import com.example.api.dto.BookingCreateDto;
import com.example.api.dto.BookingDto;
import com.example.api.dto.ItemDto;
import com.example.api.dto.UserDto;
import com.example.api.dto.enums.BookingStatus;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final ItemMapper itemMapper;
    private final UserMapper userMapper;

    public Booking toBooking(BookingCreateDto bookingCreateDto, Item item, User booker) {
        if ( bookingCreateDto == null && item == null && booker == null ) {
            return null;
        }

        Booking booking = new Booking();

        if ( bookingCreateDto != null ) {
            booking.setStart( bookingCreateDto.getStart() );
            booking.setEnd( bookingCreateDto.getEnd() );
        }
        booking.setItem( item );
        booking.setBooker( booker );
        booking.setStatus( BookingStatus.WAITING );

        return booking;
    }

    public BookingDto toBookingDto(Booking booking) {
        if ( booking == null ) {
            return null;
        }

        long id = 0L;
        LocalDateTime start = null;
        LocalDateTime end = null;
        ItemDto item = null;
        UserDto booker = null;
        BookingStatus status = null;

        if ( booking.getId() != null ) {
            id = booking.getId();
        }
        start = booking.getStart();
        end = booking.getEnd();
        item = itemMapper.toItemDto( booking.getItem() );
        booker = userMapper.toUserDto( booking.getBooker() );
        status = booking.getStatus();

        BookingDto bookingDto = new BookingDto( id, start, end, item, booker, status );

        return bookingDto;
    }

    public List<BookingDto> toBookingDto(List<Booking> bookings) {
        if ( bookings == null ) {
            return null;
        }

        List<BookingDto> list = new ArrayList<BookingDto>( bookings.size() );
        for ( Booking booking : bookings ) {
            list.add( toBookingDto( booking ) );
        }

        return list;
    }
}
