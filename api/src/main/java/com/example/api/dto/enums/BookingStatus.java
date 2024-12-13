package com.example.api.dto.enums;

/**
 * Статус бронирования.
 */
public enum BookingStatus {

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
