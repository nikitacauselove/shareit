package com.example.gateway.controller

import com.example.api.BookingApi
import com.example.api.dto.BookingCreateDto
import com.example.api.dto.BookingDto
import com.example.api.dto.enums.BookingState
import com.example.gateway.client.BookingClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [BookingApi.PATH])
class BookingController(
    val bookingClient: BookingClient
) : BookingApi {

    override fun create(bookingCreateDto: BookingCreateDto?, userId: Long?): BookingDto? {
        return bookingClient.create(bookingCreateDto, userId)
    }

    override fun approveOrReject(id: Long?, userId: Long?, approved: Boolean?): BookingDto? {
        return bookingClient.approveOrReject(id, userId, approved)
    }

    override fun findById(id: Long?, userId: Long?): BookingDto? {
        return bookingClient.findById(id, userId)
    }

    override fun findAllByBookerId(userId: Long?, state: BookingState?, from: Int?, size: Int?): List<BookingDto?>? {
        return bookingClient.findAllByBookerId(userId, state, from, size)
    }

    override fun findAllByOwnerId(userId: Long?, state: BookingState?, from: Int?, size: Int?): List<BookingDto?>? {
        return bookingClient.findAllByOwnerId(userId, state, from, size)
    }
}
