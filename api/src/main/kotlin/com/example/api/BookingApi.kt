package com.example.api

import com.example.api.dto.BookingCreateDto
import com.example.api.dto.BookingDto
import com.example.api.dto.enums.BookingState
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Запросы на бронирование", description = "Взаимодействие с запросами на бронирование")
@Validated
interface BookingApi {

    @PostMapping
    @Operation(description = "Добавление нового запроса на бронирование")
    fun create(
        @RequestBody @Valid bookingCreateDto: BookingCreateDto?,
        @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long?
    ): BookingDto?

    @PatchMapping("/{id}")
    @Operation(description = "Подтверждение или отклонение запроса на бронирование")
    fun approveOrReject(
        @PathVariable id: Long?,
        @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long?,
        @Parameter(description = "Подтверждение или отклонение запроса на бронирование") @RequestParam approved: Boolean?
    ): BookingDto?

    @GetMapping("/{id}")
    @Operation(description = "Получение информации о запросе на бронирование")
    fun findById(@PathVariable id: Long?, @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long?): BookingDto?

    @GetMapping
    @Operation(description = "Получение пользователем списка всех его запросов на бронирование")
    fun findAllByBookerId(
        @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long?,
        @Parameter(description = "Критерий поиска запросов на бронирование") @RequestParam(defaultValue = "ALL") state: BookingState?,
        @RequestParam(defaultValue = "0") @PositiveOrZero from: Int?,
        @RequestParam(defaultValue = "10") @Positive size: Int?
    ): List<BookingDto?>?

    @GetMapping("/owner")
    @Operation(description = "Получение владельцем списка всех запросов на бронирование для всех его предметов")
    fun findAllByOwnerId(
        @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long?,
        @Parameter(description = "Критерий поиска запросов на бронирование") @RequestParam(defaultValue = "ALL") state: BookingState?,
        @RequestParam(defaultValue = "0") @PositiveOrZero from: Int?,
        @RequestParam(defaultValue = "10") @Positive size: Int?
    ): List<BookingDto?>?

    companion object {
        const val PATH: String = "v1/bookings"
    }
}
