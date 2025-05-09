package com.example.server.repository

import com.example.server.entity.Booking
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import java.time.LocalDateTime

/**
 * Репозиторий для взаимодействия с запросами на бронирование.
 */
interface BookingRepository : JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    fun findAllByItem_Owner_Id(ownerId: Long, pageable: Pageable): List<Booking>

    fun findAllByItemId(itemId: Long): List<Booking>

    fun existsByBookerIdAndItemIdAndEndBefore(bookerId: Long, itemId: Long, end: LocalDateTime): Boolean

    companion object {
        const val NOT_FOUND = "Запрос на бронирование с указанным идентификатором не найден"
    }
}
