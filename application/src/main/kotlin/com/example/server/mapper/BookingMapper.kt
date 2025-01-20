package com.example.server.mapper

import com.example.api.dto.BookingCreateDto
import com.example.api.dto.BookingDto
import com.example.server.repository.entity.Booking
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [ItemMapper::class, UserMapper::class])
interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "WAITING")
    fun toBooking(bookingCreateDto: BookingCreateDto, item: Item, booker: User): Booking

    fun toBookingDto(booking: Booking): BookingDto

    fun toBookingDto(bookingList: List<Booking>): List<BookingDto>
}
