package com.example.server.mapper

import com.example.api.model.BookingCreateDto
import com.example.api.model.BookingDto
import com.example.api.model.BookingShortDto
import com.example.server.entity.Booking
import com.example.server.entity.BookingStatus
import com.example.server.entity.Item
import com.example.server.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import java.time.LocalDateTime

@Mapper(componentModel = "spring", uses = [ItemMapper::class, UserMapper::class])
interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "WAITING")
    fun toEntity(bookingCreateDto: BookingCreateDto, item: Item, booker: User): Booking

    fun toDto(booking: Booking): BookingDto

    fun toDto(bookingList: List<Booking>): List<BookingDto>
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
