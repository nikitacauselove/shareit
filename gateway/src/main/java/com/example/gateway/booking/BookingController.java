package com.example.gateway.booking;

import com.example.gateway.booking.dto.BookingCreationDto;
import com.example.gateway.booking.model.BookingState;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static com.example.gateway.exception.GlobalControllerExceptionHandler.FROM_ERROR_MESSAGE;
import static com.example.gateway.exception.GlobalControllerExceptionHandler.SIZE_ERROR_MESSAGE;
import static com.example.gateway.user.UserController.X_SHARER_USER_ID;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Validated
public class BookingController {
    private final BookingClient bookingClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid BookingCreationDto bookingCreationDto, @RequestHeader(X_SHARER_USER_ID) long bookerId) {
        return bookingClient.create(bookingCreationDto, bookerId);
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveOrReject(@PathVariable long bookingId, @RequestHeader(X_SHARER_USER_ID) long ownerId, @RequestParam boolean approved) {
        return bookingClient.approveOrReject(bookingId, ownerId, approved);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> findById(@PathVariable long bookingId, @RequestHeader(X_SHARER_USER_ID) long userId) {
        return bookingClient.findById(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> findAllByBookerId(@RequestHeader(X_SHARER_USER_ID) long bookerId,
                                                    @RequestParam(defaultValue = "ALL") String state,
                                                    @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) int from,
                                                    @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) int size) {
        return bookingClient.findAllByBookerId(bookerId, BookingState.from(state), from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) long ownerId,
                                                   @RequestParam(defaultValue = "ALL") String state,
                                                   @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) int from,
                                                   @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) int size) {
        return bookingClient.findAllByOwnerId(ownerId, BookingState.from(state), from, size);
    }
}
