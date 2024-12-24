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
    public BookingDto create(BookingCreateDto bookingCreateDto, Long userId) {
        return bookingMapper.toBookingDto(bookingService.create(bookingCreateDto, userId));
    }

    @Override
    public BookingDto approveOrReject(Long id, Long userId, Boolean approved) {
        return bookingMapper.toBookingDto(bookingService.approveOrReject(id, userId, approved));
    }

    @Override
    public BookingDto findById(Long id, Long userId) {
        return bookingMapper.toBookingDto(bookingService.findById(id, userId));
    }

    @Override
    public List<BookingDto> findAllByBookerId(Long userId, BookingState state, Integer from, Integer size) {
        return bookingMapper.toBookingDto(bookingService.findAllByBookerId(userId, state, from, size));
    }

    @Override
    public List<BookingDto> findAllByOwnerId(Long userId, BookingState state, Integer from, Integer size) {
        return bookingMapper.toBookingDto(bookingService.findAllByOwnerId(userId, state, from, size));
    }
}
