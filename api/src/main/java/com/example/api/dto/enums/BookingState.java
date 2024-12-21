package com.example.api.dto.enums;

/**
 * Критерий поиска запросов на бронирование.
 */
public enum BookingState {

    /**
     * Все.
     */
    ALL,

    /**
     * Текущие.
     */
    CURRENT,

    /**
     * Будущие.
     */
    FUTURE,

    /**
     * Завершённые.
     */
    PAST,

    /**
     * Отклонённые.
     */
    REJECTED,

    /**
     * Ожидающие подтверждения.
     */
    WAITING
}
