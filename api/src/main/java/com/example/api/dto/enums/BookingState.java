package com.example.api.dto.enums;

/**
 * Критерий поиска бронирования.
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
