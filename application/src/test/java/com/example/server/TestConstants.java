package com.example.server;

import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.BookingStatus;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TestConstants {
    public static final User FIRST_USER = new User(1L, "updateName", "updateName@user.com");
    public static final User SECOND_USER = new User(2L, "user", "user@user.com");
    public static final User THIRD_USER = new User(3L, "other", "other@other.com");
    public static final User FOURTH_USER = new User(4L, "practicum", "practicum@yandex.ru");

    public static final ItemRequest FIRST_ITEM_REQUEST = new ItemRequest(1L, "Хотел бы воспользоваться щёткой для обуви", FIRST_USER, LocalDateTime.now());

    public static final Item FIRST_ITEM = new Item(1L, "Аккумуляторная дрель", "Аккумуляторная дрель + аккумулятор", true, FIRST_USER, null);
    public static final Item SECOND_ITEM = new Item(2L, "Отвертка", "Аккумуляторная отвертка", true, SECOND_USER, null);
    public static final Item THIRD_ITEM = new Item(3L, "Клей Момент", "Тюбик суперклея марки Момент", true, SECOND_USER, null);
    public static final Item FOURTH_ITEM = new Item(4L, "Кухонный стол", "Стол для празднования", true, FOURTH_USER, null);
    public static final Item FIFTH_ITEM = new Item(5L, "Щётка для обуви", "Стандартная щётка для обуви", true, SECOND_USER, FIRST_ITEM_REQUEST);

    public static final LocalDateTime CURRENT_TIME = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    public static final LocalDateTime START_DATE = CURRENT_TIME.plusSeconds(1);
    public static final LocalDateTime END_DATE = CURRENT_TIME.plusSeconds(2);

    public static final Booking FIRST_BOOKING = new Booking(1L, START_DATE, END_DATE, SECOND_ITEM, FIRST_USER, BookingStatus.APPROVED);
    public static final Booking SECOND_BOOKING = new Booking(2L, START_DATE.plusDays(1), END_DATE.plusDays(2), SECOND_ITEM, FIRST_USER, BookingStatus.APPROVED);
    public static final Booking THIRD_BOOKING = new Booking(3L, START_DATE.plusDays(1), END_DATE.plusDays(1).plusHours(1), FIRST_ITEM, SECOND_USER, BookingStatus.WAITING);
    public static final Booking FOURTH_BOOKING = new Booking(4L, START_DATE.plusHours(1), END_DATE.plusHours(2), SECOND_ITEM, THIRD_USER, BookingStatus.APPROVED);
    public static final Booking FIFTH_BOOKING = new Booking(5L, START_DATE, END_DATE.plusDays(1), THIRD_ITEM, FIRST_USER, BookingStatus.WAITING);
    public static final Booking SIXTH_BOOKING = new Booking(6L, START_DATE, END_DATE, SECOND_ITEM, FIRST_USER, BookingStatus.APPROVED);
    public static final Booking SEVENTH_BOOKING = new Booking(7L, START_DATE.plusDays(10), END_DATE.plusDays(11), FIRST_ITEM, THIRD_USER, BookingStatus.APPROVED);
    public static final Booking EIGHT_BOOKING = new Booking(8L, START_DATE, END_DATE.plusHours(1), FOURTH_ITEM, FIRST_USER, BookingStatus.APPROVED);
}
