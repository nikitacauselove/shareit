package com.example.server.item;

import com.example.api.dto.BookingShortDto;
import com.example.api.dto.CommentDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.api.dto.enums.BookingStatus;
import com.example.server.TestConstants;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.CommentRepository;
import com.example.server.repository.entity.Comment;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.entity.Item;
import com.example.server.service.impl.ItemServiceImpl;
import com.example.server.repository.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {
    @InjectMocks
    private ItemServiceImpl itemService;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ItemRepository itemRepository;

    @Test
    public void create() {
        Item item = new Item(1L, "Дрель", "Простая дрель", true, TestConstants.FIRST_USER, null);
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(item);

        Assertions.assertEquals(item, itemService.create(item));
    }

    @Test
    public void update() {
        Item existingItem = new Item(1L, "Дрель", "Простая дрель", true, TestConstants.FIRST_USER, null);
        Item updatedItem = new Item(1L, "Дрель+", "Аккумуляторная дрель", false, TestConstants.FIRST_USER, null);
        Mockito.when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingItem));
        Mockito.when(itemRepository.save(Mockito.any(Item.class))).thenReturn(updatedItem);

        Assertions.assertEquals(updatedItem, itemService.update(updatedItem));
    }

    @Test
    public void updateWithOtherUser() {
        User otherUser = new User(3L, "user", "user@user.com");
        Item existingItem = new Item(1L, "Дрель+", "Аккумуляторная дрель", false, TestConstants.FIRST_USER, null);
        Item updatedItem = new Item(1L, "Дрель", "Простая дрель", false, otherUser, null);
        Mockito.when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(existingItem));

        Assertions.assertThrows(NotFoundException.class, () -> itemService.update(updatedItem));
    }

    @Test
    public void findById() {
        Item item = new Item(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, TestConstants.FIRST_USER, null);
        Mockito.when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(item));

        Assertions.assertEquals(item, itemService.findById(1L));
    }

    @Test
    public void findByIdUnknown() {
        Mockito.when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundException.class, () -> itemService.findById(100L));
    }

    @Test
    public void findByIdWithBooking() {
        Item secondItem = new Item(2L, "Отвертка", "Аккумуляторная отвертка", true, TestConstants.SECOND_USER, null);
        Booking booking = new Booking(1L, TestConstants.CURRENT_TIME.minusSeconds(2), TestConstants.CURRENT_TIME.minusSeconds(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking secondBooking = new Booking(2L, TestConstants.START_DATE.plusDays(1), TestConstants.END_DATE.plusDays(2), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.APPROVED);
        Booking sixthBooking = new Booking(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(1), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.APPROVED);
        Comment comment = new Comment(1L, "Add comment from user1", TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, TestConstants.CURRENT_TIME.plusSeconds(1));
        CommentDto commentDto = new CommentDto(1L, "Add comment from user1", "updateName", TestConstants.CURRENT_TIME.plusSeconds(1));
        BookingShortDto fourthBookingDto = new BookingShortDto(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), 3L);
        BookingShortDto sixthBookingDto = new BookingShortDto(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(1), 1L);
        ItemDtoWithBookings itemDtoWithBookings = new ItemDtoWithBookings(2L, "Отвертка", "Аккумуляторная отвертка", true, null, null, List.of(commentDto));
        ItemDtoWithBookings itemDtoWithBookingsAndCommentsWithBookings = new ItemDtoWithBookings(2L, "Отвертка", "Аккумуляторная отвертка", true, sixthBookingDto, fourthBookingDto, List.of(commentDto));
        Mockito.when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(secondItem));
        Mockito.when(bookingRepository.findAllByItemId(Mockito.anyLong())).thenReturn(List.of(booking, secondBooking, fourthBooking, sixthBooking));
        Mockito.when(commentRepository.findAllByItemId(Mockito.anyLong())).thenReturn(List.of(comment));

        Assertions.assertEquals(itemDtoWithBookingsAndCommentsWithBookings, itemService.findByIdWithBooking(2L, 2L));
        Assertions.assertEquals(itemDtoWithBookings, itemService.findByIdWithBooking(2L, 1L));
    }

    @Test
    public void findByIdWithoutBooking() {
        Item secondItem = new Item(2L, "Отвертка", "Аккумуляторная отвертка", true, TestConstants.SECOND_USER, null);
        Booking fourthBooking = new Booking(4L, TestConstants.START_DATE.plusHours(1), TestConstants.END_DATE.plusHours(2), TestConstants.SECOND_ITEM, TestConstants.THIRD_USER, BookingStatus.REJECTED);
        Booking sixthBooking = new Booking(6L, TestConstants.START_DATE.minusSeconds(1), TestConstants.END_DATE.minusSeconds(1), TestConstants.SECOND_ITEM, TestConstants.FIRST_USER, BookingStatus.WAITING);
        ItemDtoWithBookings itemDtoWithBookingsAndCommentsWithBookings = new ItemDtoWithBookings(2L, "Отвертка", "Аккумуляторная отвертка", true, null, null, Collections.emptyList());
        Mockito.when(itemRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(secondItem));
        Mockito.when(bookingRepository.findAllByItemId(Mockito.anyLong())).thenReturn(List.of(fourthBooking, sixthBooking));

        Assertions.assertEquals(itemDtoWithBookingsAndCommentsWithBookings, itemService.findByIdWithBooking(2L, 2L));
    }

    @Test
    public void findAllByOwnerId() {
        Item item = new Item(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, TestConstants.FIRST_USER, null);
        ItemDtoWithBookings itemDtoWithBookings = new ItemDtoWithBookings(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, null, null, Collections.emptyList());
        Mockito.when(itemRepository.findAllByOwnerId(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(List.of(item));
        Mockito.when(bookingRepository.findAllByOwnerId(Mockito.anyLong(), Mockito.any(Pageable.class))).thenReturn(Collections.emptyList());
        Mockito.when(commentRepository.findAllByOwnerId(Mockito.anyLong())).thenReturn(Collections.emptyList());

        Assertions.assertEquals(List.of(itemDtoWithBookings), itemService.findAllByOwnerId(1L, 0, 10));
    }

    @Test
    public void search() {
        Item item = new Item(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, TestConstants.FIRST_USER, null);
        Item secondItem = new Item(2L, "Отвертка", "Аккумуляторная отвертка", true, TestConstants.SECOND_USER, null);
        Mockito.when(itemRepository.search(Mockito.anyString(), Mockito.any(Pageable.class))).thenReturn(List.of(item, secondItem));

        Assertions.assertEquals(List.of(item, secondItem), itemService.search("аккУМУляторная".toLowerCase(), 0, 10));
    }
}
