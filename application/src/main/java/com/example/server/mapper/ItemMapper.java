package com.example.server.mapper;

import com.example.api.dto.BookingShortDto;
import com.example.api.dto.CommentDto;
import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.api.dto.enums.BookingStatus;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ItemMapper {

    private static final Comparator<Booking> BY_START_ASCENDING = Comparator.comparing(Booking::getStart);
    private static final Comparator<Booking> BY_START_DESCENDING = BY_START_ASCENDING.reversed();

    private final CommentMapper commentMapper;

    public Item toItem(ItemDto itemDto, User owner, ItemRequest itemRequest) {
        if ( itemDto == null && owner == null && itemRequest == null ) {
            return null;
        }

        Item item = new Item();

        if ( itemDto != null ) {
            item.setName( itemDto.getName() );
            item.setDescription( itemDto.getDescription() );
            item.setAvailable( itemDto.getAvailable() );
        }
        item.setOwner( owner );
        item.setRequest( itemRequest );

        return item;
    }

    public Item updateItem(ItemDto itemDto, Item item) {
        if ( itemDto == null ) {
            return item;
        }

        if ( itemDto.getName() != null ) {
            item.setName( itemDto.getName() );
        }
        if ( itemDto.getDescription() != null ) {
            item.setDescription( itemDto.getDescription() );
        }
        if ( itemDto.getAvailable() != null ) {
            item.setAvailable( itemDto.getAvailable() );
        }

        return item;
    }

    public ItemDto toItemDto(Item item) {
        if ( item == null ) {
            return null;
        }

        Long requestId = null;
        Long id = null;
        String name = null;
        String description = null;
        Boolean available = null;

        requestId = itemRequestId( item );
        id = item.getId();
        name = item.getName();
        description = item.getDescription();
        available = item.getAvailable();

        ItemDto itemDto = new ItemDto( id, name, description, available, requestId );

        return itemDto;
    }

    public List<ItemDto> toItemDto(List<Item> items) {
        if ( items == null ) {
            return null;
        }

        List<ItemDto> list = new ArrayList<ItemDto>( items.size() );
        for ( Item item : items ) {
            list.add( toItemDto( item ) );
        }

        return list;
    }

    public ItemDtoWithBookings toItemDtoWithBookings(Item item, List<Booking> bookings, List<Comment> comments) {
        BookingShortDto lastBooking = findLastBooking(bookings);
        BookingShortDto nextBooking = findNextBooking(bookings);

        return new ItemDtoWithBookings(item.getId(), item.getName(), item.getDescription(), item.getAvailable(), lastBooking, nextBooking, commentMapper.toCommentDto(comments));
    }

    public ItemDtoWithBookings toItemDtoWithBookings(Item item, List<Comment> comments) {
        if ( item == null && comments == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String description = null;
        boolean available = false;
        if ( item != null ) {
            if ( item.getId() != null ) {
                id = item.getId();
            }
            name = item.getName();
            description = item.getDescription();
            if ( item.getAvailable() != null ) {
                available = item.getAvailable();
            }
        }
        List<CommentDto> comments1 = null;
        comments1 = commentMapper.toCommentDto( comments );

        BookingShortDto lastBooking = null;
        BookingShortDto nextBooking = null;

        ItemDtoWithBookings itemDtoWithBookings = new ItemDtoWithBookings( id, name, description, available, lastBooking, nextBooking, comments1 );

        return itemDtoWithBookings;
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

    private Long itemRequestId(Item item) {
        ItemRequest request = item.getRequest();
        if ( request == null ) {
            return null;
        }
        return request.getId();
    }

    protected ItemDtoWithBookings itemToItemDtoWithBookings(Item item, List<Booking> bookings, List<Comment> comments) {
        if ( item == null ) {
            return null;
        }

        long id = 0L;
        String name = null;
        String description = null;
        boolean available = false;

        if ( item.getId() != null ) {
            id = item.getId();
        }
        name = item.getName();
        description = item.getDescription();
        if ( item.getAvailable() != null ) {
            available = item.getAvailable();
        }

        BookingShortDto lastBooking = null;
        BookingShortDto nextBooking = null;
        List<CommentDto> comments1 = null;

        ItemDtoWithBookings itemDtoWithBookings = new ItemDtoWithBookings( id, name, description, available, lastBooking, nextBooking, comments1 );

        return itemDtoWithBookings;
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
