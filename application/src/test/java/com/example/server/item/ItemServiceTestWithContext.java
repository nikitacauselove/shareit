package com.example.server.item;

import com.example.server.TestConstants;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.BookingStatus;
import com.example.server.repository.CommentRepository;
import com.example.server.dto.CommentDto;
import com.example.server.repository.entity.Comment;
import com.example.server.dto.ItemDtoWithBookings;
import com.example.server.repository.entity.Item;
import com.example.server.repository.UserRepository;
import com.example.server.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Collections;
import java.util.List;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@SpringBootTest
public class ItemServiceTestWithContext {
    private final ItemService itemService;
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @BeforeEach
    public void beforeEach() {
        userRepository.save(TestConstants.FIRST_USER);
        userRepository.save(TestConstants.SECOND_USER);
        userRepository.save(TestConstants.THIRD_USER);
        userRepository.save(TestConstants.FOURTH_USER);
    }

    @Test
    public void create() {
        Item item = new Item(1L, "Дрель", "Простая дрель", true, TestConstants.FIRST_USER, null);

        Assertions.assertEquals(item, itemService.create(item));
    }

    @Test
    public void update() {
        Item item = new Item(1L, "Дрель", "Простая дрель", true, TestConstants.FIRST_USER, null);
        Item updatedItem = new Item(1L, "Дрель+", "Аккумуляторная дрель", false, TestConstants.FIRST_USER, null);
        itemRepository.save(item);

        Assertions.assertEquals(updatedItem, itemService.update(updatedItem));
    }

    @Test
    public void findById() {
        Item item = new Item(1L, "Дрель", "Простая дрель", true, TestConstants.FIRST_USER, null);
        itemRepository.save(item);

        Assertions.assertEquals(item, itemService.findById(1));
    }

    @Test
    public void findByIdWithBooking() {
        Item item = new Item(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, TestConstants.FIRST_USER, null);
        Item secondItem = new Item(2L, "Отвертка", "Аккумуляторная отвертка", true, TestConstants.SECOND_USER, null);
        Item thirdItem = new Item(3L, "Клей Момент", "Тюбик суперклея марки Момент", true, TestConstants.SECOND_USER, null);
        Booking booking = new Booking(1L, TestConstants.CURRENT_TIME.minusSeconds(2), TestConstants.CURRENT_TIME.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking thirdBooking = new Booking(3L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(1).plusHours(1), TestConstants.FIRST_ITEM, TestConstants.SECOND_USER, BookingStatus.WAITING);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking fifthBooking = new Booking(5L, TestConstants.START_DATE, TestConstants.END_DATE.plusDays(1), TestConstants.THIRD_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        Booking sixthBooking = new Booking(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(1), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Comment comment = new Comment(1L, "Add comment from user1", TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, TestConstants.CURRENT_TIME.plusSeconds(1));
        CommentDto commentDto = new CommentDto(1L, "Add comment from user1", "updateName", TestConstants.CURRENT_TIME.plusSeconds(1));
        ItemDtoWithBookings.BookingDto fourthBookingDto = new ItemDtoWithBookings.BookingDto(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), 3L);
        ItemDtoWithBookings.BookingDto sixthBookingDto = new ItemDtoWithBookings.BookingDto(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(1), 1L);
        ItemDtoWithBookings itemDtoWithBookings = new ItemDtoWithBookings(2L, "Отвертка", "Аккумуляторная отвертка", true, null, null, List.of(commentDto));
        ItemDtoWithBookings itemDtoWithBookingsAndCommentsWithBookings = new ItemDtoWithBookings(2L, "Отвертка", "Аккумуляторная отвертка", true, sixthBookingDto, fourthBookingDto, List.of(commentDto));
        itemRepository.save(item);
        itemRepository.save(secondItem);
        itemRepository.save(thirdItem);
        bookingRepository.save(booking);
        bookingRepository.save(secondBooking);
        bookingRepository.save(thirdBooking);
        bookingRepository.save(fourthBooking);
        bookingRepository.save(fifthBooking);
        bookingRepository.save(sixthBooking);
        commentRepository.save(comment);

        Assertions.assertEquals(itemDtoWithBookingsAndCommentsWithBookings, itemService.findByIdWithBooking(2, 2));
        Assertions.assertEquals(itemDtoWithBookings, itemService.findByIdWithBooking(2, 1));
    }

    @Test
    public void findAllByOwnerId() {
        Item item = new Item(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, TestConstants.FIRST_USER, null);
        ItemDtoWithBookings itemDtoWithBookings = new ItemDtoWithBookings(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, null, null, Collections.emptyList());
        itemRepository.save(item);

        Assertions.assertEquals(List.of(itemDtoWithBookings), itemService.findAllByOwnerId(1, 0, 10));
    }

    @Test
    public void search() {
        Item item = new Item(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, TestConstants.FIRST_USER, null);
        Item secondItem = new Item(2L, "Отвертка", "Аккумуляторная отвертка", true, TestConstants.SECOND_USER, null);
        itemRepository.save(item);
        itemRepository.save(secondItem);

        Assertions.assertEquals(List.of(item, secondItem), itemService.search("аккУМУляторная".toLowerCase(), 0, 10));
    }
}
