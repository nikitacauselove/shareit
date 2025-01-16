package com.example.server.service.impl

import com.example.api.dto.BookingCreateDto
import com.example.api.dto.enums.BookingState
import com.example.api.dto.enums.BookingStatus
import com.example.server.exception.BadRequestException
import com.example.server.exception.NotFoundException
import com.example.server.mapper.BookingMapper
import com.example.server.repository.BookingRepository
import com.example.server.repository.FromSizePageRequest.Companion.of
import com.example.server.repository.ItemRepository
import com.example.server.repository.UserRepository
import com.example.server.repository.entity.Booking
import com.example.server.service.BookingService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class BookingServiceImpl(
    private val bookingMapper: BookingMapper,
    private val bookingRepository: BookingRepository,
    private val itemRepository: ItemRepository,
    private val userRepository: UserRepository
) : BookingService {

    @Transactional
    override fun create(bookingCreateDto: BookingCreateDto, userId: Long): Booking {
        val item = itemRepository.findById(bookingCreateDto.itemId)
            .orElseThrow { NotFoundException("Предмет с указанным идентификатором не найден") }
        val ownerId = item.owner!!.id
        val booker = userRepository.findById(userId)
            .orElseThrow { NotFoundException("Пользователь с указанным идентификатором не найден") }

        if (!item.available!!) {
            throw BadRequestException("Предмет с указанным идентификатором не доступен для бронирования")
        }
        if (ownerId == userId) {
            throw NotFoundException("Запрос на бронирование не может быть создан владельцем предмета")
        }
        return bookingRepository.save(bookingMapper.toBooking(bookingCreateDto, item, booker))
    }

    @Transactional
    override fun approveOrReject(id: Long, userId: Long, approved: Boolean): Booking {
        val booking = findById(id, userId)
        val ownerId = booking.item!!.owner!!.id

        if (FINAL_STATUSES.contains(booking.status)) {
            throw BadRequestException("Подтверждение или отклонение запроса на бронирование не может быть выполнено повторно")
        }
        if (ownerId != userId) {
            throw NotFoundException("Подтверждение или отклонение запроса на бронирование может быть выполнено только владельцем вещи")
        }
        booking.status = if (approved) BookingStatus.APPROVED else BookingStatus.REJECTED
        return bookingRepository.save(booking)
    }

    override fun findById(id: Long, userId: Long): Booking {
        val booking = bookingRepository.findById(id)
            .orElseThrow { NotFoundException("Запрос на бронирование с указанным идентификатором не найден") }
        val bookerAndOwnerIds = java.util.List.of(booking.booker!!.id, booking.item!!.owner!!.id)

        if (!bookerAndOwnerIds.contains(userId)) {
            throw NotFoundException("Получение информации о запросе на бронирование может быть осуществлено либо автором бронирования, либо владельцем предмета, который пользователь бронирует")
        }
        return booking
    }

    override fun findAllByBookerId(userId: Long, state: BookingState, from: Int, size: Int): List<Booking> {
        val now = LocalDateTime.now()
        val pageable: Pageable = of(from, size, BY_START_DESCENDING)

        if (!userRepository.existsById(userId)) {
            throw NotFoundException("Пользователь с указанным идентификатором не найден")
        }
        return when (state) {
            BookingState.CURRENT -> bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(
                userId,
                now,
                now,
                pageable
            )

            BookingState.FUTURE -> bookingRepository.findAllByBookerIdAndStartAfter(userId, now, pageable)
            BookingState.PAST -> bookingRepository.findAllByBookerIdAndEndBefore(userId, now, pageable)
            BookingState.REJECTED -> bookingRepository.findAllByBookerIdAndStatus(
                userId,
                BookingStatus.REJECTED,
                pageable
            )

            BookingState.WAITING -> bookingRepository.findAllByBookerIdAndStatus(
                userId,
                BookingStatus.WAITING,
                pageable
            )

            else -> bookingRepository.findAllByBookerId(userId, pageable)
        }
    }

    override fun findAllByOwnerId(userId: Long, state: BookingState, from: Int, size: Int): List<Booking> {
        val now = LocalDateTime.now()
        val pageable: Pageable = of(from, size, BY_START_DESCENDING)

        if (!userRepository.existsById(userId)) {
            throw NotFoundException("Пользователь с указанным идентификатором не найден")
        }
        return when (state) {
            BookingState.CURRENT -> bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(
                userId,
                now,
                now,
                pageable
            )

            BookingState.FUTURE -> bookingRepository.findAllByOwnerIdAndStartAfter(userId, now, pageable)
            BookingState.PAST -> bookingRepository.findAllByOwnerIdAndEndBefore(userId, now, pageable)
            BookingState.REJECTED -> bookingRepository.findAllByOwnerIdAndStatus(
                userId,
                BookingStatus.REJECTED,
                pageable
            )

            BookingState.WAITING -> bookingRepository.findAllByOwnerIdAndStatus(
                userId,
                BookingStatus.WAITING,
                pageable
            )

            else -> bookingRepository.findAllByItem_Owner_Id(userId, pageable)
        }
    }

    companion object {
        private val BY_START_DESCENDING: Sort = Sort.by(Sort.Direction.DESC, "start")
        private val FINAL_STATUSES: List<BookingStatus?> =
            java.util.List.of(BookingStatus.APPROVED, BookingStatus.REJECTED)
    }
}
