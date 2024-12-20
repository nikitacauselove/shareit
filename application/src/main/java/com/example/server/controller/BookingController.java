package com.example.server.controller;

import com.example.api.BookingApi;
import com.example.api.dto.BookingCreateDto;
import com.example.api.dto.BookingDto;
import com.example.api.dto.enums.BookingState;
import com.example.server.service.BookingService;
import com.example.server.mapper.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = BookingApi.PATH)
@RequiredArgsConstructor
public class BookingController implements BookingApi {

    private final BookingMapper bookingMapper;
    private final BookingService bookingService;

    @Override
    public BookingDto create(BookingCreateDto bookingCreateDto, Long bookerId) {
        return bookingMapper.toBookingDto(bookingService.create(bookingCreateDto, bookerId));
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
        return bookingMapper.toBookingDto(bookingService.findAllByBookerId(bookerId, state, from, size));
    }

    @Override
    public List<BookingDto> findAllByOwnerId(Long ownerId, BookingState state, Integer from, Integer size) {
        return bookingMapper.toBookingDto(bookingService.findAllByOwnerId(ownerId, state, from, size));
    }
}
