package com.example.server.service

import com.example.api.model.BookingCreateDto
import com.example.api.model.BookingState
import com.example.server.repository.entity.Booking

/**
 * Сервис для взаимодействия с запросами на бронирование.
 */
interface BookingService {

    /**
     * Добавление нового запроса на бронирование.
     *
     * @param bookingCreateDto информация о запросе на бронирование
     * @param userId идентификатор пользователя
     */
    fun create(bookingCreateDto: BookingCreateDto, userId: Long): Booking

    /**
     * Подтверждение или отклонение запроса на бронирование.
     *
     * @param id идентификатор запроса на бронирование
     * @param userId идентификатор пользователя
     * @param approved подтверждение или отклонение запроса на бронирование
     */
    fun approveOrReject(id: Long, userId: Long, approved: Boolean): Booking

    /**
     * Получение информации о запросе на бронирование.
     *
     * @param id идентификатор запроса на бронирование
     * @param userId идентификатор пользователя
     */
    fun findById(id: Long, userId: Long): Booking

    /**
     * Получение пользователем списка всех его запросов на бронирование.
     *
     * @param userId идентификатор пользователя
     * @param state критерий поиска запросов на бронирование
     * @param from индекс первого элемента
     * @param size количество элементов для отображения
     */
    fun findAllByBookerId(userId: Long, state: BookingState, from: Int, size: Int): List<Booking>

    /**
     * Получение списка всех запросов на бронирование для всех предметов пользователя.
     *
     * @param userId идентификатор пользователя
     * @param state критерий поиска запросов на бронирование
     * @param from индекс первого элемента
     * @param size количество элементов для отображения
     */
    fun findAllByOwnerId(userId: Long, state: BookingState, from: Int, size: Int): List<Booking>
}
