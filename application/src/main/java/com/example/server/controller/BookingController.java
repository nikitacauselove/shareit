package com.example.server.controller;

import com.example.api.BookingApi;
import com.example.api.dto.BookingCreateDto;
import com.example.api.dto.BookingDto;
import com.example.api.dto.enums.BookingState;
import com.example.server.service.BookingService;
import com.example.server.service.ItemService;
import com.example.server.service.UserService;
import com.example.server.mapper.BookingMapper;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = BookingApi.PATH)
@RequiredArgsConstructor
public class BookingController implements BookingApi {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public BookingDto create(BookingCreateDto bookingCreateDto, Long bookerId) {
        User booker = userService.findById(bookerId);
        Item item = itemService.findById(bookingCreateDto.itemId());
        Booking booking = bookingMapper.toBooking(bookingCreateDto, item, booker);

        return bookingMapper.toBookingDto(bookingService.create(booking));
    }

    @Override
    public BookingDto approveOrReject(Long bookingId, Long ownerId, Boolean approved) {
        return bookingMapper.toBookingDto(bookingService.approveOrReject(bookingId, ownerId, approved));
    }

    @Override
    public BookingDto findById(Long bookingId, Long userId) {
        return bookingMapper.toBookingDto(bookingService.findById(bookingId, userId));
    }

    @Override
    public List<BookingDto> findAllByBookerId(Long bookerId, BookingState state, Integer from, Integer size) {
        User booker = userService.findById(bookerId);

        return bookingMapper.toBookingDto(bookingService.findAllByBookerId(booker.getId(), state, from, size));
    }

    @Override
    public List<BookingDto> findAllByOwnerId(Long ownerId, BookingState state, Integer from, Integer size) {
        User owner = userService.findById(ownerId);

        return bookingMapper.toBookingDto(bookingService.findAllByOwnerId(owner.getId(), state, from, size));
    }
}
