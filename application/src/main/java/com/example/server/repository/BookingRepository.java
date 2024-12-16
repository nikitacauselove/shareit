package com.example.server.repository;

import com.example.api.dto.enums.BookingStatus;
import com.example.server.repository.entity.Booking;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для взаимодействия с запросами на бронирование.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfter(Long bookerId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartAfter(Long bookerId, LocalDateTime start, Pageable pageable);

    List<Booking> findAllByBookerIdAndEndBefore(Long bookerId, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByBookerIdAndStatus(Long bookerId, BookingStatus status, Pageable pageable);

    List<Booking> findAllByBookerId(Long bookerId, Pageable pageable);

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.start < :start and booking.end > :end")
    List<Booking> findAllByOwnerIdAndStartBeforeAndEndAfter(@Param("ownerId") Long ownerId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.start > :start")
    List<Booking> findAllByOwnerIdAndStartAfter(@Param("ownerId") Long ownerId, @Param("start") LocalDateTime start, Pageable pageable);

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.end < :end")
    List<Booking> findAllByOwnerIdAndEndBefore(@Param("ownerId") Long ownerId, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.status = :status")
    List<Booking> findAllByOwnerIdAndStatus(@Param("ownerId") Long ownerId, @Param("status") BookingStatus status, Pageable pageable);

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId")
    List<Booking> findAllByOwnerId(@Param("ownerId") Long ownerId, Pageable pageable);

    List<Booking> findAllByItemId(Long itemId);

    boolean existsByBookerIdAndItemIdAndEndBefore(Long bookerId, Long itemId, LocalDateTime end);
}
