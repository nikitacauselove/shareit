package com.example.server.service.impl;

import com.example.api.dto.BookingCreateDto;
import com.example.api.dto.enums.BookingState;
import com.example.api.dto.enums.BookingStatus;
import com.example.server.mapper.BookingMapper;
import com.example.server.repository.FromSizePageRequest;
import com.example.server.exception.BadRequestException;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.UserRepository;
import com.example.server.repository.entity.Booking;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.User;
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

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    private static final Sort BY_START_DESCENDING = Sort.by(Sort.Direction.DESC, "start");

    @Override
    @Transactional
    public Booking create(BookingCreateDto bookingCreateDto, Long bookerId) {
        User booker = userRepository.findById(bookerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден."));
        Item item = itemRepository.findById(bookingCreateDto.itemId())
                .orElseThrow(() -> new NotFoundException("Предмет с указанным идентификатором не найден."));
        Booking booking = bookingMapper.toBooking(bookingCreateDto, item, booker);

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
        User booker = userRepository.findById(bookerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден."));;
        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = FromSizePageRequest.of(from, size, BY_START_DESCENDING);

        return switch (state) {
            case CURRENT -> bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(bookerId, now, now, pageable);
            case FUTURE -> bookingRepository.findAllByBookerIdAndStartAfter(bookerId, now, pageable);
            case PAST -> bookingRepository.findAllByBookerIdAndEndBefore(bookerId, now, pageable);
            case REJECTED -> bookingRepository.findAllByBookerIdAndStatus(bookerId, BookingStatus.REJECTED, pageable);
            case WAITING -> bookingRepository.findAllByBookerIdAndStatus(bookerId, BookingStatus.WAITING, pageable);
            default -> bookingRepository.findAllByBookerId(bookerId, pageable);
        };
    }

    @Override
    public List<Booking> findAllByOwnerId(Long ownerId, BookingState state, Integer from, Integer size) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден."));;
        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = FromSizePageRequest.of(from, size, BY_START_DESCENDING);

        return switch (state) {
            case CURRENT -> bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(ownerId, now, now, pageable);
            case FUTURE -> bookingRepository.findAllByOwnerIdAndStartAfter(ownerId, now, pageable);
            case PAST -> bookingRepository.findAllByOwnerIdAndEndBefore(ownerId, now, pageable);
            case REJECTED -> bookingRepository.findAllByOwnerIdAndStatus(ownerId, BookingStatus.REJECTED, pageable);
            case WAITING -> bookingRepository.findAllByOwnerIdAndStatus(ownerId, BookingStatus.WAITING, pageable);
            default -> bookingRepository.findAllByOwnerId(ownerId, pageable);
        };
    }
}
