package com.example.server.booking;

import com.example.server.booking.dto.BookingCreationDto;
import com.example.server.booking.dto.BookingDto;
import com.example.server.booking.dto.BookingMapper;
import com.example.server.booking.model.Booking;
import com.example.server.booking.model.BookingState;
import com.example.server.item.ItemService;
import com.example.server.item.model.Item;
import com.example.server.user.UserService;
import com.example.server.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.server.user.UserController.X_SHARER_USER_ID;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
    private final BookingService bookingService;
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<BookingDto> create(@RequestBody BookingCreationDto bookingCreationDto, @RequestHeader(X_SHARER_USER_ID) long bookerId) {
        User booker = userService.findById(bookerId);
        Item item = itemService.findById(bookingCreationDto.getItemId());
        Booking booking = BookingMapper.toBooking(bookingCreationDto, item, booker);

        log.info("Добавление нового запроса на бронирование пользователем с идентификатором {}.", bookerId);
        return ResponseEntity.ok(BookingMapper.toBookingDto(bookingService.create(booking)));
    }

    @PatchMapping("/{bookingId}")
    public ResponseEntity<BookingDto> approveOrReject(@PathVariable long bookingId, @RequestHeader(X_SHARER_USER_ID) long ownerId, @RequestParam boolean approved) {
        log.info("Подтверждение или отклонение запроса на бронирование с идентификатором {} пользователем с идентификатором {}.", bookingId, ownerId);
        return ResponseEntity.ok(BookingMapper.toBookingDto(bookingService.approveOrReject(bookingId, ownerId, approved)));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDto> findById(@PathVariable long bookingId, @RequestHeader(X_SHARER_USER_ID) long userId) {
        log.info("Получение данных о конкретном бронировании (включая его статус) с идентификатором {} пользователем с идентификатором {}.", bookingId, userId);
        return ResponseEntity.ok(BookingMapper.toBookingDto(bookingService.findById(bookingId, userId)));
    }

    @GetMapping
    public ResponseEntity<List<BookingDto>> findAllByBookerId(@RequestHeader(X_SHARER_USER_ID) long bookerId, @RequestParam String state, @RequestParam int from, @RequestParam int size) {
        User booker = userService.findById(bookerId);

        log.info("Получение списка всех бронирований текущего пользователя с идентификатором {}.", bookerId);
        return ResponseEntity.ok(BookingMapper.toBookingDto(bookingService.findAllByBookerId(booker.getId(), BookingState.from(state), from, size)));
    }

    @GetMapping("/owner")
    public ResponseEntity<List<BookingDto>> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) long ownerId, @RequestParam String state, @RequestParam int from, @RequestParam int size) {
        User owner = userService.findById(ownerId);

        log.info("Получение списка бронирований для всех вещей текущего пользователя с идентификатором {}.", ownerId);
        return ResponseEntity.ok(BookingMapper.toBookingDto(bookingService.findAllByOwnerId(owner.getId(), BookingState.from(state), from, size)));
    }
}
