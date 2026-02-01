package com.example.server.controller

import com.example.api.BookingApi
import com.example.api.model.BookingCreateDto
import com.example.api.model.BookingDto
import com.example.api.model.BookingState
import com.example.server.mapper.toDto
import com.example.server.service.BookingService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [BookingApi.PATH])
class BookingController(
    private val bookingService: BookingService
) : BookingApi {

    override fun create(bookingCreateDto: BookingCreateDto, userId: Long): BookingDto {
        return bookingService.create(bookingCreateDto, userId).toDto()
    }

    override fun approveOrReject(id: Long, userId: Long, approved: Boolean): BookingDto {
        return bookingService.approveOrReject(id, userId, approved).toDto()
    }

    override fun findById(id: Long, userId: Long): BookingDto {
        return bookingService.findById(id, userId).toDto()
    }

    override fun findAllByBookerId(userId: Long, state: BookingState, from: Int, size: Int): List<BookingDto> {
        return bookingService.findAllByBookerId(userId, state, from, size).toDto()
    }

    override fun findAllByOwnerId(userId: Long, state: BookingState, from: Int, size: Int): List<BookingDto> {
        return bookingService.findAllByOwnerId(userId, state, from, size).toDto()
    }
}
