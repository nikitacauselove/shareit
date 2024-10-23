package com.example.gateway.controller;

import com.example.api.BookingApi;
import com.example.api.dto.BookingCreationDto;
import com.example.gateway.BookingState;
import com.example.gateway.client.BookingClient;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BookingController implements BookingApi {

    private final BookingClient bookingClient;

    @Override
    public ResponseEntity<Object> create(BookingCreationDto bookingCreationDto, long bookerId) {
        return bookingClient.create(bookingCreationDto, bookerId);
    }

    @Override
    public ResponseEntity<Object> approveOrReject(long bookingId, long ownerId, boolean approved) {
        return bookingClient.approveOrReject(bookingId, ownerId, approved);
    }

    @Override
    public ResponseEntity<Object> findById(long bookingId, long userId) {
        return bookingClient.findById(bookingId, userId);
    }

    @Override
    public ResponseEntity<Object> findAllByBookerId(long bookerId, String state, int from, int size) {
        return bookingClient.findAllByBookerId(bookerId, BookingState.from(state), from, size);
    }

    @Override
    public ResponseEntity<Object> findAllByOwnerId(long ownerId, String state, int from, int size) {
        return bookingClient.findAllByOwnerId(ownerId, BookingState.from(state), from, size);
    }
}
