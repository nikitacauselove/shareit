package com.example.server.mapper

import com.example.api.dto.BookingCreateDto
import com.example.api.dto.BookingDto
import com.example.api.dto.enums.BookingStatus
import com.example.server.repository.entity.Booking
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.User
import org.springframework.stereotype.Component

@Component
class BookingMapper(
    private val itemMapper: ItemMapper,
    private val userMapper: UserMapper
) {

    fun toBooking(bookingCreateDto: BookingCreateDto, item: Item, booker: User): Booking {
        return Booking(
            id = null,
            start = bookingCreateDto.start,
            end = bookingCreateDto.end,
            item = item,
            booker = booker,
            status = BookingStatus.WAITING
        )
    }

    fun toBookingDto(booking: Booking): BookingDto {
        val id = booking.id!!
        val start = booking.start
        val end = booking.end
        val item = itemMapper.toItemDto(booking.item)
        val booker = userMapper.toUserDto(booking.booker)
        val status = booking.status

        return BookingDto(id, start, end, item, booker, status)
    }

    fun toBookingDto(bookings: List<Booking>): List<BookingDto> {
        val list: MutableList<BookingDto> = ArrayList(bookings.size)

        for (booking in bookings) {
            list.add(toBookingDto(booking))
        }
        return list
    }
}
