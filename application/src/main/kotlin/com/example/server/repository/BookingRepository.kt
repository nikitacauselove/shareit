package com.example.server.repository

import com.example.server.repository.entity.Booking
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * Репозиторий для взаимодействия с запросами на бронирование.
 */
@Repository
interface BookingRepository : JpaRepository<Booking, Long>, JpaSpecificationExecutor<Booking> {

    fun findAllByItem_Owner_Id(ownerId: Long, pageable: Pageable): List<Booking>

    fun findAllByItemId(itemId: Long): List<Booking>

    fun existsByBookerIdAndItemIdAndEndBefore(bookerId: Long, itemId: Long, end: LocalDateTime): Boolean
}
