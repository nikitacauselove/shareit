package com.example.server.service.impl

import com.example.api.model.BookingCreateDto
import com.example.api.model.BookingState
import com.example.server.exception.BadRequestException
import com.example.server.exception.NotFoundException
import com.example.server.mapper.BookingMapper
import com.example.server.repository.BookingRepository
import com.example.server.repository.FromSizePageRequest.Companion.of
import com.example.server.repository.ItemRepository
import com.example.server.repository.UserRepository
import com.example.server.entity.Booking
import com.example.server.entity.BookingStatus
import com.example.server.repository.specification.BookingSpecification
import com.example.server.service.BookingService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookingServiceImpl(
    private val bookingMapper: BookingMapper,
    private val bookingSpecification: BookingSpecification,
    private val bookingRepository: BookingRepository,
    private val itemRepository: ItemRepository,
    private val userRepository: UserRepository
) : BookingService {

    @Transactional
    override fun create(bookingCreateDto: BookingCreateDto, userId: Long): Booking {
        val item = itemRepository.findById(bookingCreateDto.itemId)
            .orElseThrow { NotFoundException(ItemRepository.NOT_FOUND) }
        val booker = userRepository.findById(userId)
            .orElseThrow { NotFoundException(UserRepository.NOT_FOUND) }

        if (!item.available) {
            throw BadRequestException("Предмет с указанным идентификатором недоступен для бронирования")
        }
        if (userId == item.owner.id) {
            throw NotFoundException("Запрос на бронирование не может быть создан владельцем предмета")
        }
        return bookingRepository.save(bookingMapper.toEntity(bookingCreateDto, item, booker))
    }

    @Transactional
    override fun approveOrReject(id: Long, userId: Long, approved: Boolean): Booking {
        val booking = findById(id, userId)

        if (FINAL_STATUSES.contains(booking.status)) {
            throw BadRequestException("Подтверждение или отклонение запроса на бронирование не может быть выполнено повторно")
        }
        if (userId != booking.item.owner.id) {
            throw NotFoundException("Подтверждение или отклонение запроса на бронирование может быть выполнено только владельцем предмета")
        }
        booking.status = if (approved) BookingStatus.APPROVED else BookingStatus.REJECTED
        return booking
    }

    @Transactional(readOnly = true)
    override fun findById(id: Long, userId: Long): Booking {
        val booking = bookingRepository.findById(id)
            .orElseThrow { NotFoundException(BookingRepository.NOT_FOUND) }
        val bookerAndOwnerIds = listOf(booking.booker.id, booking.item.owner.id)

        if (!bookerAndOwnerIds.contains(userId)) {
            throw NotFoundException("Получение информации о запросе на бронирование может быть осуществлено либо автором бронирования, либо владельцем предмета, который пользователь бронирует")
        }
        return booking
    }

    @Transactional(readOnly = true)
    override fun findAllByBookerId(userId: Long, state: BookingState, from: Int, size: Int): List<Booking> {
        val specification = bookingSpecification.byBookerId(userId)
            .and(bookingSpecification.byState(state))
        val pageable: Pageable = of(from, size, SORT_BY_DESCENDING_START)

        if (!userRepository.existsById(userId)) {
            throw NotFoundException(UserRepository.NOT_FOUND)
        }
        return bookingRepository.findAll(specification, pageable).content
    }

    @Transactional(readOnly = true)
    override fun findAllByOwnerId(userId: Long, state: BookingState, from: Int, size: Int): List<Booking> {
        val specification = bookingSpecification.byOwnerId(userId)
            .and(bookingSpecification.byState(state))
        val pageable: Pageable = of(from, size, SORT_BY_DESCENDING_START)

        if (!userRepository.existsById(userId)) {
            throw NotFoundException(UserRepository.NOT_FOUND)
        }
        return bookingRepository.findAll(specification, pageable).content
    }

    companion object {
        private val FINAL_STATUSES = listOf(BookingStatus.APPROVED, BookingStatus.REJECTED)
        private val SORT_BY_DESCENDING_START = Sort.by(Sort.Direction.DESC, "start")
    }
}
