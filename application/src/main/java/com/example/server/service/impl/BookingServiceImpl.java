package com.example.server.service.impl;

import com.example.api.dto.enums.BookingState;
import com.example.api.dto.enums.BookingStatus;
import com.example.server.FromSizePageRequest;
import com.example.server.exception.BadRequestException;
import com.example.server.repository.entity.Booking;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.BookingRepository;
import com.example.server.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private static final Sort BY_START_DESCENDING = Sort.by(Sort.Direction.DESC, "start");

    @Override
    public Booking create(Booking booking) {
        if (!booking.getItem().isAvailable()) {
            throw new BadRequestException("Предмет с указанным идентификатором не доступен для бронирования.");
        } else if (booking.hasSameBooker(booking.getItem().getOwner().getId())) {
            throw new NotFoundException("Запрос на бронирование не может быть создан владельцем вещи.");
        }
        return bookingRepository.save(booking);
    }

    @Override
    @Transactional
    public Booking approveOrReject(Long bookingId, Long ownerId, Boolean approved) {
        Booking booking = findById(bookingId, ownerId);

        if (booking.isApprovedOrRejected()) {
            throw new BadRequestException("Подтверждение или отклонение запроса на бронирование не может быть выполнено повторно.");
        } else if (!booking.hasSameOwner(ownerId)) {
            throw new NotFoundException("Подтверждение или отклонение запроса на бронирование может быть выполнено только владельцем вещи.");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findById(Long bookingId, Long userId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new NotFoundException("Запрос на бронирование для пользователя с указанным идентификатором не найден."));

        if (booking.hasSameBooker(userId) || booking.hasSameOwner(userId)) {
            return booking;
        }
        throw new NotFoundException("Получение данных о конкретном бронировании может быть выполнено либо автором бронирования, либо владельцем вещи, к которой относится бронирование.");
    }

    @Override
    public List<Booking> findAllByBookerId(Long bookerId, BookingState state, Integer from, Integer size) {
        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = FromSizePageRequest.of(from, size, BY_START_DESCENDING);

        switch (state) {
            case CURRENT:
                return bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(bookerId, now, now, pageable);
            case FUTURE:
                return bookingRepository.findAllByBookerIdAndStartAfter(bookerId, now, pageable);
            case PAST:
                return bookingRepository.findAllByBookerIdAndEndBefore(bookerId, now, pageable);
            case REJECTED:
                return bookingRepository.findAllByBookerIdAndStatus(bookerId, BookingStatus.REJECTED, pageable);
            case WAITING:
                return bookingRepository.findAllByBookerIdAndStatus(bookerId, BookingStatus.WAITING, pageable);
            default:
                return bookingRepository.findAllByBookerId(bookerId, pageable);
        }
    }

    @Override
    public List<Booking> findAllByOwnerId(Long ownerId, BookingState state, Integer from, Integer size) {
        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = FromSizePageRequest.of(from, size, BY_START_DESCENDING);

        switch (state) {
            case CURRENT:
                return bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(ownerId, now, now, pageable);
            case FUTURE:
                return bookingRepository.findAllByOwnerIdAndStartAfter(ownerId, now, pageable);
            case PAST:
                return bookingRepository.findAllByOwnerIdAndEndBefore(ownerId, now, pageable);
            case REJECTED:
                return bookingRepository.findAllByOwnerIdAndStatus(ownerId, BookingStatus.REJECTED, pageable);
            case WAITING:
                return bookingRepository.findAllByOwnerIdAndStatus(ownerId, BookingStatus.WAITING, pageable);
            default:
                return bookingRepository.findAllByOwnerId(ownerId, pageable);
        }
    }
}
