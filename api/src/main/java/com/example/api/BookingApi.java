package com.example.api;

import com.example.api.dto.BookingCreationDto;
import com.example.api.dto.BookingDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.api.Constant.FROM_ERROR_MESSAGE;
import static com.example.api.Constant.SIZE_ERROR_MESSAGE;
import static com.example.api.Constant.X_SHARER_USER_ID;

@Validated
public interface BookingApi {

    String PATH = "/bookings";

    @PostMapping
    BookingDto create(@RequestBody @Valid BookingCreationDto bookingCreationDto, @RequestHeader(X_SHARER_USER_ID) Long bookerId);

    @PatchMapping("/{bookingId}")
    BookingDto approveOrReject(@PathVariable Long bookingId, @RequestHeader(X_SHARER_USER_ID) Long ownerId, @RequestParam Boolean approved);

    @GetMapping("/{bookingId}")
    BookingDto findById(@PathVariable Long bookingId, @RequestHeader(X_SHARER_USER_ID) Long userId);

    @GetMapping
    List<BookingDto> findAllByBookerId(@RequestHeader(X_SHARER_USER_ID) Long bookerId,
                                       @RequestParam(defaultValue = "ALL") String state,
                                       @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) Integer from,
                                       @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) Integer size);

    @GetMapping("/owner")
    List<BookingDto> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) Long ownerId,
                                      @RequestParam(defaultValue = "ALL") String state,
                                      @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) Integer from,
                                      @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) Integer size);
}
