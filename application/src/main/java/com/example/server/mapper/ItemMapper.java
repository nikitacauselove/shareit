package com.example.server.mapper;

import com.example.api.dto.ItemDto;
import com.example.server.dto.ItemDtoWithBookings;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ItemMapper {
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
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public ItemDtoWithBookings toItemDtoWithBookings(Item item, List<Booking> bookings, List<Comment> comments) {
        ItemDtoWithBookings.BookingDto lastBooking = ItemDtoWithBookings.findLastBooking(bookings);
        ItemDtoWithBookings.BookingDto nextBooking = ItemDtoWithBookings.findNextBooking(bookings);

        return new ItemDtoWithBookings(item.getId(), item.getName(), item.getDescription(), item.isAvailable(), lastBooking, nextBooking, CommentMapper.toCommentDto(comments));
    }

    public ItemDtoWithBookings toItemDtoWithBookings(Item item, List<Comment> comments) {
        return new ItemDtoWithBookings(item.getId(), item.getName(), item.getDescription(), item.isAvailable(), null, null, CommentMapper.toCommentDto(comments));
    }

    public List<ItemDtoWithBookings> toItemDtoWithBookings(List<Item> items, List<Booking> bookings, List<Comment> comments) {
        return items.stream()
                .map(item -> {
                    List<Booking> listOfBookings = bookings.stream().filter(booking -> booking.hasSameItem(item)).collect(Collectors.toList());
                    List<Comment> listOfComments = comments.stream().filter(comment -> comment.hasSameItem(item)).collect(Collectors.toList());

                    return ItemMapper.toItemDtoWithBookings(item, listOfBookings, listOfComments);
                })
                .collect(Collectors.toList());
    }
}
