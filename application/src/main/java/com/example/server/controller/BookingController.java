package com.example.server.controller;

import com.example.api.BookingApi;
import com.example.api.dto.BookingCreationDto;
import com.example.api.dto.BookingDto;
import com.example.api.dto.enums.BookingState;
import com.example.server.service.BookingService;
import com.example.server.mapper.BookingMapper;
import com.example.server.repository.entity.Booking;
import com.example.server.service.ItemService;
import com.example.server.repository.entity.Item;
import com.example.server.service.UserService;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = BookingApi.PATH)
@RequiredArgsConstructor
public class BookingController implements BookingApi {

    private final BookingService bookingService;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public BookingDto create(BookingCreationDto bookingCreationDto, Long bookerId) {
        User booker = userService.findById(bookerId);
        Item item = itemService.findById(bookingCreationDto.itemId());
        Booking booking = BookingMapper.toBooking(bookingCreationDto, item, booker);

        log.info("Добавление нового запроса на бронирование пользователем с идентификатором {}.", bookerId);
        return BookingMapper.toBookingDto(bookingService.create(booking));
    }

    @Override
    public BookingDto approveOrReject(Long bookingId, Long ownerId, Boolean approved) {
        log.info("Подтверждение или отклонение запроса на бронирование с идентификатором {} пользователем с идентификатором {}.", bookingId, ownerId);
        return BookingMapper.toBookingDto(bookingService.approveOrReject(bookingId, ownerId, approved));
    }

    @Override
    public BookingDto findById(Long bookingId, Long userId) {
        log.info("Получение данных о конкретном бронировании (включая его статус) с идентификатором {} пользователем с идентификатором {}.", bookingId, userId);
        return BookingMapper.toBookingDto(bookingService.findById(bookingId, userId));
    }

    @Override
    public List<BookingDto> findAllByBookerId(Long bookerId, String state, Integer from, Integer size) {
        User booker = userService.findById(bookerId);

        log.info("Получение списка всех бронирований текущего пользователя с идентификатором {}.", bookerId);
        return BookingMapper.toBookingDto(bookingService.findAllByBookerId(booker.getId(), BookingState.from(state), from, size));
    }

    @Override
    public List<BookingDto> findAllByOwnerId(Long ownerId, String state, Integer from, Integer size) {
        User owner = userService.findById(ownerId);

        log.info("Получение списка бронирований для всех вещей текущего пользователя с идентификатором {}.", ownerId);
        return BookingMapper.toBookingDto(bookingService.findAllByOwnerId(owner.getId(), BookingState.from(state), from, size));
    }
}
