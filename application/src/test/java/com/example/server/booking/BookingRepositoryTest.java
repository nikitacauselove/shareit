package com.example.server.booking;

import com.example.api.dto.enums.BookingStatus;
import com.example.server.TestConstants;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

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
    public void findAllByBookerIdAndStartBeforeAndEndAfter() {
        Booking booking = new Booking(1L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking thirdBooking = new Booking(3L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(1).plusHours(1), TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.REJECTED);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.REJECTED);
        Booking sixthBooking = new Booking(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking seventhBooking = new Booking(7L, TestConstants.START_DATE.plusDays(10), TestConstants.END_DATE.plusDays(11), TestConstants.FIRST_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking eightBooking = new Booking(8L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusHours(1), TestConstants.FOURTH_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);
        bookingRepository.save(thirdBooking);
        bookingRepository.save(fourthBooking);
        bookingRepository.save(fifthBooking);
        bookingRepository.save(sixthBooking);
        bookingRepository.save(seventhBooking);
        bookingRepository.save(eightBooking);

        Assertions.assertEquals(List.of(fifthBooking, eightBooking), bookingRepository.findAllByBookerIdAndStartBeforeAndEndAfter(1, LocalDateTime.now(), LocalDateTime.now(), PageRequest.of(0, 10)));
    }

    @Test
    public void findAllByBookerIdAndEndBefore() {
        Booking booking = new Booking(1L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking thirdBooking = new Booking(3L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(1).plusHours(1), TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.REJECTED);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.REJECTED);
        Booking sixthBooking = new Booking(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);
        bookingRepository.save(thirdBooking);
        bookingRepository.save(fourthBooking);
        bookingRepository.save(fifthBooking);
        bookingRepository.save(sixthBooking);

        Assertions.assertEquals(List.of(booking, sixthBooking), bookingRepository.findAllByBookerIdAndEndBefore(1, LocalDateTime.now(), PageRequest.of(0, 10)));
    }

    @Test
    public void findAllByBookerIdAndStatus() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking thirdBooking = new Booking(3L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(1).plusHours(1), TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.REJECTED);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE, TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);
        bookingRepository.save(thirdBooking);
        bookingRepository.save(fourthBooking);
        bookingRepository.save(fifthBooking);

        Assertions.assertEquals(List.of(fifthBooking), bookingRepository.findAllByBookerIdAndStatus(1, BookingStatus.WAITING, PageRequest.of(0, 10)));
    }

    @Test
    public void findAllByBookerId() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);

        Assertions.assertEquals(List.of(booking, secondBooking), bookingRepository.findAllByBookerId(1, PageRequest.of(0, 10)));
    }

    @Test
    public void findAllByOwnerIdAndStartBeforeAndEndAfter() {
        Booking booking = new Booking(1L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking thirdBooking = new Booking(3L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(1).plusHours(1), TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.REJECTED);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.REJECTED);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);
        bookingRepository.save(thirdBooking);
        bookingRepository.save(fourthBooking);
        bookingRepository.save(fifthBooking);

        Assertions.assertEquals(List.of(fifthBooking), bookingRepository.findAllByOwnerIdAndStartBeforeAndEndAfter(2, LocalDateTime.now(), LocalDateTime.now(), PageRequest.of(0, 10)));
    }

    @Test
    public void findAllByOwnerIdAndStartAfter() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);

        Assertions.assertEquals(List.of(secondBooking), bookingRepository.findAllByOwnerIdAndStartAfter(2, LocalDateTime.now(), PageRequest.of(0, 10)));
    }

    @Test
    public void findAllByOwnerIdAndEndBefore() {
        Booking booking = new Booking(1L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking thirdBooking = new Booking(3L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(1).plusHours(1), TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.REJECTED);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.REJECTED);
        Booking sixthBooking = new Booking(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);
        bookingRepository.save(thirdBooking);
        bookingRepository.save(fourthBooking);
        bookingRepository.save(fifthBooking);
        bookingRepository.save(sixthBooking);

        Assertions.assertEquals(List.of(booking, sixthBooking), bookingRepository.findAllByOwnerIdAndEndBefore(2, LocalDateTime.now(), PageRequest.of(0, 10)));
    }

    @Test
    public void findAllByOwnerIdAndStatus() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking thirdBooking = new Booking(3L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(1).plusHours(1), TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.REJECTED);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE, TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);
        bookingRepository.save(thirdBooking);
        bookingRepository.save(fourthBooking);
        bookingRepository.save(fifthBooking);

        Assertions.assertEquals(List.of(fifthBooking), bookingRepository.findAllByOwnerIdAndStatus(2, BookingStatus.WAITING, PageRequest.of(0, 10)));
    }

    @Test
    public void findAllByOwnerId() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);

        Assertions.assertEquals(List.of(booking, secondBooking), bookingRepository.findAllByOwnerId(2, PageRequest.of(0, 10)));
    }

    @Test
    public void findAllByItemId() {
        Booking booking = new Booking(1L, TestConstants.START_DATE, TestConstants.END_DATE, TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking thirdBooking = new Booking(3L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(1).plusHours(1), TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.REJECTED);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);
        bookingRepository.save(thirdBooking);
        bookingRepository.save(fourthBooking);

        Assertions.assertEquals(List.of(booking, secondBooking, fourthBooking), bookingRepository.findAllByItemId(2));
    }

    @Test
    public void existsByBookerIdAndItemIdAndEndBefore() {
        Booking booking = new Booking(1L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        bookingRepository.save(booking);

        Assertions.assertTrue(bookingRepository.existsByBookerIdAndItemIdAndEndBefore(1, 2, LocalDateTime.now()));
    }
}
