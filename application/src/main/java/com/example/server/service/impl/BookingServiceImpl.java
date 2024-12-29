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

    private static final Sort BY_START_DESCENDING = Sort.by(Sort.Direction.DESC, Booking.Fields.start);
    private static final List<BookingStatus> FINAL_STATUSES = List.of(BookingStatus.APPROVED, BookingStatus.REJECTED);

    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Booking create(BookingCreateDto bookingCreateDto, Long userId) {
        Item item = itemRepository.findById(bookingCreateDto.itemId())
                .orElseThrow(() -> new NotFoundException("Предмет с указанным идентификатором не найден"));
        Long ownerId = item.getOwner().getId();
        User booker = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден"));

        if (!item.getAvailable()) {
            throw new BadRequestException("Предмет с указанным идентификатором не доступен для бронирования");
        }
        if (ownerId.equals(userId)) {
            throw new NotFoundException("Запрос на бронирование не может быть создан владельцем предмета");
        }
        return bookingRepository.save(bookingMapper.toBooking(bookingCreateDto, item, booker));
    }

    @Override
    @Transactional
    public Booking approveOrReject(Long id, Long userId, Boolean approved) {
        Booking booking = findById(id, userId);
        Long ownerId = booking.getItem().getOwner().getId();

        if (FINAL_STATUSES.contains(booking.getStatus())) {
            throw new BadRequestException("Подтверждение или отклонение запроса на бронирование не может быть выполнено повторно");
        }
        if (!ownerId.equals(userId)) {
            throw new NotFoundException("Подтверждение или отклонение запроса на бронирование может быть выполнено только владельцем вещи");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking findById(Long id, Long userId) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Запрос на бронирование с указанным идентификатором не найден"));
        List<Long> bookerAndOwnerIds = List.of(booking.getBooker().getId(), booking.getItem().getOwner().getId());

        if (!bookerAndOwnerIds.contains(userId)) {
            throw new NotFoundException("Получение информации о запросе на бронирование может быть осуществлено либо автором бронирования, либо владельцем предмета, который пользователь бронирует");
        }
        return booking;
    }

    @Override
    public List<Booking> findAllByBookerId(Long userId, BookingState state, Integer from, Integer size) {
        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = FromSizePageRequest.of(from, size, BY_START_DESCENDING);

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с указанным идентификатором не найден");
        }
        return switch (state) {
            case CURRENT -> bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(userId, now, now, pageable);
            case FUTURE -> bookingRepository.findAllByBookerIdAndStartAfter(userId, now, pageable);
            case PAST -> bookingRepository.findAllByBookerIdAndEndBefore(userId, now, pageable);
            case REJECTED -> bookingRepository.findAllByBookerIdAndStatus(userId, BookingStatus.REJECTED, pageable);
            case WAITING -> bookingRepository.findAllByBookerIdAndStatus(userId, BookingStatus.WAITING, pageable);
            default -> bookingRepository.findAllByBookerId(userId, pageable);
        };
    }

    @Override
    public List<Booking> findAllByOwnerId(Long userId, BookingState state, Integer from, Integer size) {
        LocalDateTime now = LocalDateTime.now();
        Pageable pageable = FromSizePageRequest.of(from, size, BY_START_DESCENDING);

        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь с указанным идентификатором не найден");
        }
        return switch (state) {
            case CURRENT -> bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(userId, now, now, pageable);
            case FUTURE -> bookingRepository.findAllByOwnerIdAndStartAfter(userId, now, pageable);
            case PAST -> bookingRepository.findAllByOwnerIdAndEndBefore(userId, now, pageable);
            case REJECTED -> bookingRepository.findAllByOwnerIdAndStatus(userId, BookingStatus.REJECTED, pageable);
            case WAITING -> bookingRepository.findAllByOwnerIdAndStatus(userId, BookingStatus.WAITING, pageable);
            default -> bookingRepository.findAllByOwnerId(userId, pageable);
        };
    }
}
