package com.example.api;

import com.example.api.dto.BookingCreationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static com.example.api.Constant.FROM_ERROR_MESSAGE;
import static com.example.api.Constant.SIZE_ERROR_MESSAGE;
import static com.example.api.Constant.X_SHARER_USER_ID;

@Validated
public interface BookingApi {

    String PATH = "/bookings";

    @PostMapping
    ResponseEntity<Object> create(@RequestBody @Valid BookingCreationDto bookingCreationDto, @RequestHeader(X_SHARER_USER_ID) long bookerId);

    @PatchMapping("/{bookingId}")
    ResponseEntity<Object> approveOrReject(@PathVariable long bookingId, @RequestHeader(X_SHARER_USER_ID) long ownerId, @RequestParam boolean approved);

    @GetMapping("/{bookingId}")
    ResponseEntity<Object> findById(@PathVariable long bookingId, @RequestHeader(X_SHARER_USER_ID) long userId);

    @GetMapping
    ResponseEntity<Object> findAllByBookerId(@RequestHeader(X_SHARER_USER_ID) long bookerId,
                                             @RequestParam(defaultValue = "ALL") String state,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) int from,
                                             @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) int size);

    @GetMapping("/owner")
    ResponseEntity<Object> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) long ownerId,
                                            @RequestParam(defaultValue = "ALL") String state,
                                            @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) int from,
                                            @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) int size);
}
