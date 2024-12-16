package com.example.server.booking;

import com.example.api.dto.enums.BookingState;
import com.example.api.dto.enums.BookingStatus;
import com.example.server.TestConstants;
import com.example.server.exception.BadRequestException;
import com.example.server.repository.entity.Booking;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.entity.Item;
import com.example.server.repository.BookingRepository;
import com.example.server.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @Test
    public void create() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(booking);

        Assertions.assertEquals(booking, bookingService.create(booking));
    }

    @Test
    public void createUnavailable() {
        Item unavailableItem = new Item(2L, "Отвертка", "Аккумуляторная отвертка", false, TestConstants.SECOND_USER, null);
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, unavailableItem, TestConstants.FIRST_USER, BookingStatus.WAITING);

        Assertions.assertThrows(BadRequestException.class, () -> bookingService.create(booking));
    }

    @Test
    public void createByOwner() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.FIRST_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);

        Assertions.assertThrows(NotFoundException.class, () -> bookingService.create(booking));
    }

    @Test
    public void approve() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking approvedBooking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(booking));
        Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(approvedBooking);

        Assertions.assertEquals(approvedBooking, bookingService.approveOrReject(1L, 2L, true));
    }

    @Test
    public void reject() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking rejectedBooking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.REJECTED);
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(booking));
        Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(rejectedBooking);

        Assertions.assertEquals(rejectedBooking, bookingService.approveOrReject(1L, 2L, false));
    }

    @Test
    public void approveOrRejectAfterApprove() {
        Booking booking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(booking));

        Assertions.assertThrows(BadRequestException.class, () -> bookingService.approveOrReject(2L, 2L, true));
    }

    @Test
    public void approveOrRejectAfterRejecte() {
        Booking booking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.REJECTED);
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(booking));

        Assertions.assertThrows(BadRequestException.class, () -> bookingService.approveOrReject(2L, 2L, true));
    }

    @Test
    public void approveOrRejectNotByOwner() {
        Booking booking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(booking));

        Assertions.assertThrows(NotFoundException.class, () -> bookingService.approveOrReject(2L, 5L, true));
    }

    @Test
    public void approveOrRejectByBooker() {
        Booking booking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(booking));

        Assertions.assertThrows(NotFoundException.class, () -> bookingService.approveOrReject(2L, 1L, true));
    }

    @Test
    public void findById() {
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(secondBooking));

        Assertions.assertEquals(secondBooking, bookingService.findById(2L, 1L));
    }

    @Test
    public void findByIdUnknown() {
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> bookingService.findById(1L, 5L));
    }

    @Test
    public void findByIdByOtherUser() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.WAITING);
        Mockito.when(bookingRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(booking));

        Assertions.assertThrows(NotFoundException.class, () -> bookingService.findById(1L, 5L));
    }

    @Test
    public void findAllByBookerId() {
        Booking booking = new Booking(1L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking sixthBooking = new Booking(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking eightBooking = new Booking(8L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusHours(1), TestConstants.FOURTH_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Mockito.when(bookingRepository.findAllByBookerId(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(List.of(secondBooking, booking));
        Mockito.when(bookingRepository.findAllByBookerIdAndStartAfter(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(secondBooking));
        Mockito.when(bookingRepository.findAllByBookerIdAndStatus(Mockito.anyLong(), Mockito.any(BookingStatus.class), Mockito.any(Pageable.class))).thenReturn(List.of(fifthBooking));
        Mockito.when(bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(fifthBooking, eightBooking));
        Mockito.when(bookingRepository.findAllByBookerIdAndEndBefore(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(booking, sixthBooking));

        Assertions.assertEquals(List.of(secondBooking, booking), bookingService.findAllByBookerId(1L, BookingState.ALL, 0, 10));
        Assertions.assertEquals(List.of(secondBooking), bookingService.findAllByBookerId(1L, BookingState.FUTURE, 0, 10));
        Assertions.assertEquals(List.of(fifthBooking), bookingService.findAllByBookerId(1L, BookingState.WAITING, 0, 10));
        Assertions.assertEquals(List.of(fifthBooking), bookingService.findAllByBookerId(1L, BookingState.REJECTED, 0, 10));
        Assertions.assertEquals(List.of(fifthBooking, eightBooking), bookingService.findAllByBookerId(1L, BookingState.CURRENT, 0, 10));
        Assertions.assertEquals(List.of(booking, sixthBooking), bookingService.findAllByBookerId(1L, BookingState.PAST, 0, 10));
    }

    @Test
    public void findAllByOwnerId() {
        Booking booking = new Booking(1L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking sixthBooking = new Booking(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking eightBooking = new Booking(8L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusHours(1), TestConstants.FOURTH_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Mockito.when(bookingRepository.findAllByOwnerId(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(List.of(secondBooking, booking));
        Mockito.when(bookingRepository.findAllByOwnerIdAndStartAfter(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(secondBooking));
        Mockito.when(bookingRepository.findAllByOwnerIdAndStatus(Mockito.anyLong(), Mockito.any(BookingStatus.class), Mockito.any(Pageable.class))).thenReturn(List.of(fifthBooking));
        Mockito.when(bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(fifthBooking));
        Mockito.when(bookingRepository.findAllByOwnerIdAndEndBefore(Mockito.anyLong(), Mockito.any(LocalDateTime.class), Mockito.any(Pageable.class))).thenReturn(List.of(booking, sixthBooking));


        Assertions.assertEquals(List.of(secondBooking, booking), bookingService.findAllByOwnerId(2L, BookingState.ALL, 0, 10));
        Assertions.assertEquals(List.of(secondBooking), bookingService.findAllByOwnerId(2L, BookingState.FUTURE, 0, 10));
        Assertions.assertEquals(List.of(fifthBooking), bookingService.findAllByOwnerId(2L, BookingState.WAITING, 0, 10));
        Assertions.assertEquals(List.of(fifthBooking), bookingService.findAllByOwnerId(2L, BookingState.REJECTED, 0, 10));
        Assertions.assertEquals(List.of(fifthBooking), bookingService.findAllByOwnerId(2L, BookingState.CURRENT, 0, 10));
        Assertions.assertEquals(List.of(booking, sixthBooking), bookingService.findAllByOwnerId(2L, BookingState.PAST, 0, 10));
    }
}
