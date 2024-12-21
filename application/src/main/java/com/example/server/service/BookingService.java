package com.example.server.service;

import com.example.api.dto.BookingCreateDto;
import com.example.api.dto.enums.BookingState;
import com.example.server.repository.entity.Booking;

import java.util.List;

/**
 * Сервис для взаимодействия с запросами на бронирование.
 */
public interface BookingService {

    /**
     * Добавление нового запроса на бронирование.
     * @param bookingCreateDto информация о запросе на бронирование
     * @param bookerId идентификатор пользователя
     */
    Booking create(BookingCreateDto bookingCreateDto, Long bookerId);

    /**
     * Подтверждение или отклонение запроса на бронирование.
     * @param bookingId идентификатор запроса на бронирование
     * @param ownerId идентификатор пользователя
     * @param approved
     */
    Booking approveOrReject(Long bookingId, Long ownerId, Boolean approved);

    /**
     * Получение информации о запросе на бронирование.
     * @param bookingId идентификатор запроса на бронирование
     * @param userId идентификатор пользователя
     */
    Booking findById(Long bookingId, Long userId);

    /**
     * Получение списка всех запросов на бронирование пользователя.
     * @param bookerId идентификатор пользователя
     * @param state критерий поиска запросов на бронирование
     */
    List<Booking> findAllByBookerId(Long bookerId, BookingState state, Integer from, Integer size);

    /**
     * Получение списка всех запросов на бронирование для всех предметов пользователя.
     * @param ownerId идентификатор пользователя
     * @param state критерий поиска запросов на бронирование
     */
    List<Booking> findAllByOwnerId(Long ownerId, BookingState state, Integer from, Integer size);
}
