package com.example.server.mapper;

import com.example.api.dto.BookingShortDto;
import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.BookingUtils;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private final CommentMapper commentMapper;

    public Item toItem(ItemDto itemDto, User owner, ItemRequest itemRequest) {
        return new Item(null, itemDto.name(), itemDto.description(), itemDto.available(), owner, itemRequest);
    }

    public Item toItem(Item item, ItemDto itemDto, User owner) {
        return new Item(
                item.getId(),
                itemDto.name() != null ? itemDto.name() : item.getName(),
                itemDto.description() != null ? itemDto.description() : item.getDescription(),
                itemDto.available() != null ? itemDto.available() : item.isAvailable(),
                owner,
                item.getRequest()
        );
    }

    public ItemDto toItemDto(Item item) {
        Long requestId = item.getRequest() == null ? null : item.getRequest().getId();

        return new ItemDto(item.getId(), item.getName(), item.getDescription(), item.isAvailable(), requestId);
    }

    public List<ItemDto> toItemDto(List<Item> items) {
        return items.stream()
                .map(this::toItemDto)
                .collect(Collectors.toList());
    }

    public ItemDtoWithBookings toItemDtoWithBookings(Item item, List<Booking> bookings, List<Comment> comments) {
        BookingShortDto lastBooking = BookingUtils.findLastBooking(bookings);
        BookingShortDto nextBooking = BookingUtils.findNextBooking(bookings);

        return new ItemDtoWithBookings(item.getId(), item.getName(), item.getDescription(), item.isAvailable(), lastBooking, nextBooking, commentMapper.toCommentDto(comments));
    }

    public ItemDtoWithBookings toItemDtoWithBookings(Item item, List<Comment> comments) {
        return new ItemDtoWithBookings(item.getId(), item.getName(), item.getDescription(), item.isAvailable(), null, null, commentMapper.toCommentDto(comments));
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
}
