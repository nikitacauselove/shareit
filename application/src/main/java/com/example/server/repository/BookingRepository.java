package com.example.server.repository;

import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.BookingStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findAllByBookerIdAndStartBeforeAndEndAfter(long bookerId, LocalDateTime start, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByBookerIdAndStartAfter(long bookerId, LocalDateTime start, Pageable pageable);

    List<Booking> findAllByBookerIdAndEndBefore(long bookerId, LocalDateTime end, Pageable pageable);

    List<Booking> findAllByBookerIdAndStatus(long bookerId, BookingStatus status, Pageable pageable);

    List<Booking> findAllByBookerId(long bookerId, Pageable pageable);

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.start < :start and booking.end > :end")
    List<Booking> findAllByOwnerIdAndStartBeforeAndEndAfter(@Param("ownerId") long ownerId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.start > :start")
    List<Booking> findAllByOwnerIdAndStartAfter(@Param("ownerId") long ownerId, @Param("start") LocalDateTime start, Pageable pageable);

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.end < :end")
    List<Booking> findAllByOwnerIdAndEndBefore(@Param("ownerId") long ownerId, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId and booking.status = :status")
    List<Booking> findAllByOwnerIdAndStatus(@Param("ownerId") long ownerId, @Param("status") BookingStatus status, Pageable pageable);

    @Query("select booking from Booking as booking join fetch booking.item as item join fetch item.owner as user where user.id = :ownerId")
    List<Booking> findAllByOwnerId(@Param("ownerId") long ownerId, Pageable pageable);

    List<Booking> findAllByItemId(long itemId);

    boolean existsByBookerIdAndItemIdAndEndBefore(long bookerId, long itemId, LocalDateTime end);
}
