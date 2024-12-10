package com.example.gateway.controller;

import com.example.api.BookingApi;
import com.example.api.dto.BookingCreationDto;
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
    public BookingDto create(BookingCreationDto bookingCreationDto, Long bookerId) {
        return bookingClient.create(bookingCreationDto, bookerId);
    }

    @Override
    public BookingDto approveOrReject(Long bookingId, Long ownerId, Boolean approved) {
        return bookingClient.approveOrReject(bookingId, ownerId, approved);
    }

    @Override
    public BookingDto findById(Long bookingId, Long userId) {
        return bookingClient.findById(bookingId, userId);
    }

    @Override
    public List<BookingDto> findAllByBookerId(Long bookerId, BookingState state, Integer from, Integer size) {
        return bookingClient.findAllByBookerId(bookerId, state, from, size);
    }

    @Override
    public List<BookingDto> findAllByOwnerId(Long ownerId, BookingState state, Integer from, Integer size) {
        return bookingClient.findAllByOwnerId(ownerId, state, from, size);
    }
}
