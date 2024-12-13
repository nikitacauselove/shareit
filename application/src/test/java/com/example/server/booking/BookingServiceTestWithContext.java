package com.example.server.booking;

import com.example.api.dto.enums.BookingState;
import com.example.api.dto.enums.BookingStatus;
import com.example.server.TestConstants;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.UserRepository;
import com.example.server.service.impl.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
public class BookingServiceTestWithContext {
    private final BookingServiceImpl bookingService;
    private final BookingRepository bookingRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository.save(TestConstants.FIRST_USER);
        userRepository.save(TestConstants.SECOND_USER);
        userRepository.save(TestConstants.THIRD_USER);
        userRepository.save(TestConstants.FOURTH_USER);

        itemRepository.save(TestConstants.FIRST_ITEM);
        itemRepository.save(TestConstants.SECOND_ITEM);
        itemRepository.save(TestConstants.THIRD_ITEM);
        itemRepository.save(TestConstants.FOURTH_ITEM);
    }

    @Test
    public void create() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);

        Assertions.assertEquals(booking, bookingService.create(booking));
    }

    @Test
    public void approveOrReject() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        bookingRepository.save(booking);

        Assertions.assertEquals(BookingStatus.APPROVED, bookingService.approveOrReject(1, 2, true).getStatus());
    }

    @Test
    public void findById() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);

        Assertions.assertEquals(secondBooking, bookingService.findById(2, 1));
    }

    @Test
    public void findAllByBookerId() {
        Booking booking = new Booking(1L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking thirdBooking = new Booking(3L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(1).plusHours(1), TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.REJECTED);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking sixthBooking = new Booking(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking seventhBooking = new Booking(7L, TestConstants.START_DATE.plusDays(10), TestConstants.END_DATE.plusDays(11), TestConstants.FIRST_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking eightBooking = new Booking(8L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusHours(1), TestConstants.FOURTH_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);

        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);

        Assertions.assertEquals(List.of(secondBooking, booking), bookingService.findAllByBookerId(1, BookingState.ALL, 0, 10));
        Assertions.assertEquals(List.of(secondBooking), bookingService.findAllByBookerId(1, BookingState.FUTURE, 0, 10));

        bookingRepository.save(thirdBooking);
        bookingRepository.save(fourthBooking);
        bookingRepository.save(fifthBooking);

        bookingService.approveOrReject(2, 2, true);
        secondBooking.setStatus(BookingStatus.APPROVED);

        Assertions.assertEquals(List.of(fifthBooking), bookingService.findAllByBookerId(1, BookingState.WAITING, 0, 10));

        bookingRepository.save(sixthBooking);

        bookingService.approveOrReject(5, 2, false);
        fifthBooking.setStatus(BookingStatus.REJECTED);

        Assertions.assertEquals(List.of(fifthBooking), bookingService.findAllByBookerId(1, BookingState.REJECTED, 0, 10));

        bookingService.approveOrReject(6, 2, true);
        sixthBooking.setStatus(BookingStatus.APPROVED);

        bookingRepository.save(seventhBooking);
        bookingRepository.save(eightBooking);

        Assertions.assertEquals(List.of(fifthBooking, eightBooking), bookingService.findAllByBookerId(1, BookingState.CURRENT, 0, 10));
        Assertions.assertEquals(List.of(booking, sixthBooking), bookingService.findAllByBookerId(1, BookingState.PAST, 0, 10));
    }

    @Test
    public void findAllByOwnerId() {
        Booking booking = new Booking(1L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking thirdBooking = new Booking(3L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(1).plusHours(1), TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.REJECTED);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking sixthBooking = new Booking(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking seventhBooking = new Booking(7L, TestConstants.START_DATE.plusDays(10), TestConstants.END_DATE.plusDays(11), TestConstants.FIRST_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking eightBooking = new Booking(8L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusHours(1), TestConstants.FOURTH_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);

        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);

        Assertions.assertEquals(List.of(secondBooking, booking), bookingService.findAllByOwnerId(2, BookingState.ALL, 0, 10));
        Assertions.assertEquals(List.of(secondBooking), bookingService.findAllByOwnerId(2, BookingState.FUTURE, 0, 10));

        bookingRepository.save(thirdBooking);
        bookingRepository.save(fourthBooking);
        bookingRepository.save(fifthBooking);

        bookingService.approveOrReject(2, 2, true);
        secondBooking.setStatus(BookingStatus.APPROVED);

        Assertions.assertEquals(List.of(fifthBooking), bookingService.findAllByOwnerId(2, BookingState.WAITING, 0, 10));

        bookingRepository.save(sixthBooking);

        bookingService.approveOrReject(5, 2, false);
        fifthBooking.setStatus(BookingStatus.REJECTED);

        Assertions.assertEquals(List.of(fifthBooking), bookingService.findAllByOwnerId(2, BookingState.REJECTED, 0, 10));

        bookingService.approveOrReject(6, 2, true);
        sixthBooking.setStatus(BookingStatus.APPROVED);

        bookingRepository.save(seventhBooking);
        bookingRepository.save(eightBooking);

        Assertions.assertEquals(List.of(fifthBooking), bookingService.findAllByOwnerId(2, BookingState.CURRENT, 0, 10));
        Assertions.assertEquals(List.of(booking, sixthBooking), bookingService.findAllByOwnerId(2, BookingState.PAST, 0, 10));
    }
}
