package com.example.gateway.controller;

import com.example.api.BookingApi;
import com.example.api.dto.BookingCreateDto;
import com.example.api.dto.BookingDto;
import com.example.api.dto.enums.BookingState;
import com.example.gateway.client.BookingClient;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = BookingApi.PATH)
@RequiredArgsConstructor
public class BookingController implements BookingApi {

    private final BookingClient bookingClient;

    @Override
    public BookingDto create(BookingCreateDto bookingCreateDto, Long userId) {
        return bookingClient.create(bookingCreateDto, userId);
    }

    @Override
    public BookingDto approveOrReject(Long id, Long userId, Boolean approved) {
        return bookingClient.approveOrReject(id, userId, approved);
    }

    @Override
    public BookingDto findById(Long id, Long userId) {
        return bookingClient.findById(id, userId);
    }

    @Override
    public List<BookingDto> findAllByBookerId(Long userId, BookingState state, Integer from, Integer size) {
        return bookingClient.findAllByBookerId(userId, state, from, size);
    }

    @Override
    public List<BookingDto> findAllByOwnerId(Long userId, BookingState state, Integer from, Integer size) {
        return bookingClient.findAllByOwnerId(userId, state, from, size);
    }
}
