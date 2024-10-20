package com.example.server.service;

import com.example.server.FromSizePageRequest;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.CommentRepository;
import com.example.server.repository.entity.Comment;
import com.example.server.exception.NotFoundException;
import com.example.server.dto.ItemDtoWithBookings;
import com.example.server.mapper.ItemMapper;
import com.example.server.repository.entity.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ItemService {
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Item create(Item item) {
        return itemRepository.save(item);
    }

    @Transactional
    public Item update(Item item) {
        if (findById(item.getId()).hasSameOwner(item)) {
            return itemRepository.save(item);
        }
        throw new NotFoundException("Редактировать вещь может только её владелец.");
    }

    public Item findById(long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет с указанным идентификатором не найден."));
    }

    public ItemDtoWithBookings findByIdWithBooking(long itemId, long userId) {
        Item item = findById(itemId);

        if (item.hasSameOwner(userId)) {
            return ItemMapper.toItemDtoWithBookings(item, bookingRepository.findAllByItemId(itemId), commentRepository.findAllByItemId(itemId));
        }
        return ItemMapper.toItemDtoWithBookings(item, commentRepository.findAllByItemId(itemId));
    }

    public List<ItemDtoWithBookings> findAllByOwnerId(long ownerId, int from, int size) {
        List<Item> items = itemRepository.findAllByOwnerId(ownerId, FromSizePageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id")));
        List<Booking> bookings = bookingRepository.findAllByOwnerId(ownerId, Pageable.unpaged());
        List<Comment> comments = commentRepository.findAllByOwnerId(ownerId);

        return ItemMapper.toItemDtoWithBookings(items, bookings, comments);
    }

    public List<Item> search(String text, int from, int size) {
        return itemRepository.search(text.toLowerCase(), FromSizePageRequest.of(from, size));
    }
}
