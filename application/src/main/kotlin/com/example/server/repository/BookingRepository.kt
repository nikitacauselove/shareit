package com.example.server.repository

import com.example.api.dto.enums.BookingStatus
import com.example.server.repository.entity.Booking
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

/**
 * Репозиторий для взаимодействия с запросами на бронирование.
 */
@Repository
interface BookingRepository : JpaRepository<Booking, Long> {

    fun findAllByBookerIdAndStartBeforeAndEndAfter(bookerId: Long, start: LocalDateTime, end: LocalDateTime, pageable: Pageable): List<Booking>

    fun findAllByBookerIdAndStartAfter(bookerId: Long, start: LocalDateTime, pageable: Pageable): List<Booking>

    fun findAllByBookerIdAndEndBefore(bookerId: Long, end: LocalDateTime, pageable: Pageable): List<Booking>

    fun findAllByBookerIdAndStatus(bookerId: Long, status: BookingStatus, pageable: Pageable): List<Booking>

    fun findAllByBookerId(bookerId: Long, pageable: Pageable): List<Booking>

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.start < :start and booking.end > :end")
    fun findAllByOwnerIdAndStartBeforeAndEndAfter(@Param("ownerId") ownerId: Long, @Param("start") start: LocalDateTime, @Param("end") end: LocalDateTime, pageable: Pageable): List<Booking>

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.start > :start")
    fun findAllByOwnerIdAndStartAfter(@Param("ownerId") ownerId: Long, @Param("start") start: LocalDateTime, pageable: Pageable): List<Booking>

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.end < :end")
    fun findAllByOwnerIdAndEndBefore(@Param("ownerId") ownerId: Long, @Param("end") end: LocalDateTime, pageable: Pageable): List<Booking>

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.status = :status")
    fun findAllByOwnerIdAndStatus(@Param("ownerId") ownerId: Long, @Param("status") status: BookingStatus, pageable: Pageable): List<Booking>

    fun findAllByItem_Owner_Id(@Param("ownerId") ownerId: Long, pageable: Pageable): List<Booking>

    fun findAllByItemId(itemId: Long): List<Booking>

    fun existsByBookerIdAndItemIdAndEndBefore(bookerId: Long, itemId: Long, end: LocalDateTime): Boolean
}
