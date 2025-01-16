package com.example.server.mapper

import com.example.api.dto.BookingCreateDto
import com.example.api.dto.BookingDto
import com.example.api.dto.ItemDto
import com.example.api.dto.UserDto
import com.example.api.dto.enums.BookingStatus
import com.example.server.repository.entity.Booking
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class BookingMapper(
    private val itemMapper: ItemMapper,
    private val userMapper: UserMapper
) {

    fun toBooking(bookingCreateDto: BookingCreateDto, item: Item, booker: User): Booking {
        val booking = Booking()

        booking.start = bookingCreateDto.start
        booking.end = bookingCreateDto.end
        booking.item = item
        booking.booker = booker
        booking.status = BookingStatus.WAITING

        return booking
    }

    fun toBookingDto(booking: Booking): BookingDto {
        var id = 0L
        var start: LocalDateTime? = null
        var end: LocalDateTime? = null
        var item: ItemDto? = null
        var booker: UserDto? = null
        var status: BookingStatus? = null

        id = booking.id!!
        start = booking.start
        end = booking.end
        item = itemMapper.toItemDto(booking.item!!)
        booker = userMapper.toUserDto(booking.booker!!)
        status = booking.status

        val bookingDto = BookingDto(id, start!!, end!!, item!!, booker!!, status!!)

        return bookingDto
    }

    fun toBookingDto(bookings: List<Booking>): List<BookingDto> {
        val list: MutableList<BookingDto> = ArrayList(bookings.size)

        for (booking in bookings) {
            list.add(toBookingDto(booking))
        }

        return list
    }
}
