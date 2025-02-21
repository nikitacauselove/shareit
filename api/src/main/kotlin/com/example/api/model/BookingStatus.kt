package com.example.api.model

/**
 * Статус бронирования.
 */
enum class BookingStatus {

    /**
     * Подтверждено владельцем.
     */
    APPROVED,

    /**
     * Отменено создателем.
     */
    CANCELED,

    /**
     * Отклонено владельцем.
     */
    REJECTED,

    /**
     * Ожидает одобрения.
     */
    WAITING
}
