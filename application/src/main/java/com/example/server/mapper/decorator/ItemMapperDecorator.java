package com.example.server.mapper.decorator;

import com.example.api.dto.BookingShortDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.api.dto.enums.BookingStatus;
import com.example.server.mapper.CommentMapper;
import com.example.server.mapper.ItemMapper;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.entity.Item;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Setter(onMethod_ = @Autowired)
public abstract class ItemMapperDecorator implements ItemMapper {

    private static final Comparator<Booking> BY_START_ASCENDING = Comparator.comparing(Booking::getStart);
    private static final Comparator<Booking> BY_START_DESCENDING = BY_START_ASCENDING.reversed();

    private CommentMapper commentMapper;
    private ItemMapper delegate;

    public ItemDtoWithBookings toItemDtoWithBookings(Item item, List<Booking> bookings, List<Comment> comments) {
        BookingShortDto lastBooking = findLastBooking(bookings);
        BookingShortDto nextBooking = findNextBooking(bookings);

        return new ItemDtoWithBookings(item.getId(), item.getName(), item.getDescription(), item.isAvailable(), lastBooking, nextBooking, commentMapper.toCommentDto(comments));
    }

    public List<ItemDtoWithBookings> toItemDtoWithBookings(List<Item> items, List<Booking> bookings, List<Comment> comments) {
        return items.stream()
                .map(item -> {
                    List<Booking> listOfBookings = bookings.stream().filter(booking -> booking.hasSameItem(item)).collect(Collectors.toList());
                    List<Comment> listOfComments = comments.stream().filter(comment -> comment.hasSameItem(item)).collect(Collectors.toList());

                    return this.toItemDtoWithBookings(item, listOfBookings, listOfComments);
                })
                .collect(Collectors.toList());
    }

    private BookingShortDto findLastBooking(List<Booking> bookings) {
        LocalDateTime now = LocalDateTime.now();

        return bookings.stream()
                .sorted(BY_START_DESCENDING)
                .filter(booking -> booking.getStatus() == BookingStatus.APPROVED && booking.getStart().isBefore(now))
                .findFirst()
                .map(booking -> new BookingShortDto(booking.getId(), booking.getStart(), booking.getEnd(), booking.getBooker().getId()))
                .orElse(null);
    }

    private BookingShortDto findNextBooking(List<Booking> bookings) {
        LocalDateTime now = LocalDateTime.now();

        return bookings.stream()
                .sorted(BY_START_ASCENDING)
                .filter(booking -> booking.getStatus() == BookingStatus.APPROVED && booking.getStart().isAfter(now))
                .findFirst()
                .map(booking -> new BookingShortDto(booking.getId(), booking.getStart(), booking.getEnd(), booking.getBooker().getId()))
                .orElse(null);
    }
}
