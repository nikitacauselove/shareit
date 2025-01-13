package com.example.api.dto.enums

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
