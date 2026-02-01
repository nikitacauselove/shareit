package com.example.server.mapper

import com.example.api.model.BookingCreateDto
import com.example.api.model.BookingDto
import com.example.api.model.BookingShortDto
import com.example.api.model.BookingStatus
import com.example.server.entity.Booking
import com.example.server.entity.Item
import com.example.server.entity.User
import java.time.LocalDateTime

fun BookingCreateDto.toEntity(item: Item, booker: User): Booking {
    return Booking(id = null, start = this.start, end = this.end, item = item, booker = booker, status = BookingStatus.WAITING)
}

fun Booking.toDto(): BookingDto {
    return BookingDto(id = this.id!!, start = this.start, end = this.end, item = this.item.toDto(), booker = this.booker.toDto(), status = this.status)
}

fun List<Booking>.toDto(): List<BookingDto> {
    return map { it.toDto() }
}

fun List<Booking>.findLast(): BookingShortDto? {
    val now = LocalDateTime.now()

    return this.stream()
        .sorted(Booking.BY_START_DESCENDING)
        .filter { booking: Booking -> booking.status == BookingStatus.APPROVED && booking.start.isBefore(now) }
        .findFirst()
        .map { booking: Booking -> BookingShortDto(booking.id!!, booking.start, booking.end, booking.booker.id!!) }
        .orElse(null)
}

fun List<Booking>.findNext(): BookingShortDto? {
    val now = LocalDateTime.now()

    return this.stream()
        .sorted(Booking.BY_START_ASCENDING)
        .filter { booking: Booking -> booking.status == BookingStatus.APPROVED && booking.start.isAfter(now) }
        .findFirst()
        .map { booking: Booking -> BookingShortDto(booking.id!!, booking.start, booking.end, booking.booker.id!!) }
        .orElse(null)
}
