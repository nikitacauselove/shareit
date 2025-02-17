package com.example.server.entity

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
