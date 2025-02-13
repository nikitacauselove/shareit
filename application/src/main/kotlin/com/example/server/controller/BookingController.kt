package com.example.server.controller

import com.example.api.BookingApi
import com.example.api.model.BookingCreateDto
import com.example.api.model.BookingDto
import com.example.api.model.BookingState
import com.example.server.mapper.BookingMapper
import com.example.server.service.BookingService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [BookingApi.PATH])
class BookingController(
    private val bookingMapper: BookingMapper,
    private val bookingService: BookingService
) : BookingApi {

    override fun create(bookingCreateDto: BookingCreateDto, userId: Long): BookingDto {
        return bookingMapper.toBookingDto(bookingService.create(bookingCreateDto, userId))
    }

    override fun approveOrReject(id: Long, userId: Long, approved: Boolean): BookingDto {
        return bookingMapper.toBookingDto(bookingService.approveOrReject(id, userId, approved))
    }

    override fun findById(id: Long, userId: Long): BookingDto {
        return bookingMapper.toBookingDto(bookingService.findById(id, userId))
    }

    override fun findAllByBookerId(userId: Long, state: BookingState, from: Int, size: Int): List<BookingDto> {
        return bookingMapper.toBookingDto(bookingService.findAllByBookerId(userId, state, from, size))
    }

    override fun findAllByOwnerId(userId: Long, state: BookingState, from: Int, size: Int): List<BookingDto> {
        return bookingMapper.toBookingDto(bookingService.findAllByOwnerId(userId, state, from, size))
    }
}
