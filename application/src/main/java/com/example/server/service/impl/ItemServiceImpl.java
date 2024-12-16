package com.example.server.service.impl;

import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.FromSizePageRequest;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.CommentRepository;
import com.example.server.repository.entity.Comment;
import com.example.server.exception.NotFoundException;
import com.example.server.mapper.ItemMapper;
import com.example.server.repository.entity.Item;
import com.example.server.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {
    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    @Override
    @Transactional
    public Item create(Item item) {
        return itemRepository.save(item);
    }

    @Override
    @Transactional
    public Item update(Item item) {
        if (findById(item.getId()).hasSameOwner(item)) {
            return itemRepository.save(item);
        }
        throw new NotFoundException("Редактировать вещь может только её владелец.");
    }

    @Override
    public Item findById(Long itemId) {
        return itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет с указанным идентификатором не найден."));
    }

    @Override
    public ItemDtoWithBookings findByIdWithBooking(Long itemId, Long userId) {
        Item item = findById(itemId);

        if (item.hasSameOwner(userId)) {
            return itemMapper.toItemDtoWithBookings(item, bookingRepository.findAllByItemId(itemId), commentRepository.findAllByItemId(itemId));
        }
        return itemMapper.toItemDtoWithBookings(item, commentRepository.findAllByItemId(itemId));
    }

    @Override
    public List<ItemDtoWithBookings> findAllByOwnerId(Long ownerId, Integer from, Integer size) {
        List<Item> items = itemRepository.findAllByOwnerId(ownerId, FromSizePageRequest.of(from, size, Sort.by(Sort.Direction.ASC, "id")));
        List<Booking> bookings = bookingRepository.findAllByOwnerId(ownerId, Pageable.unpaged());
        List<Comment> comments = commentRepository.findAllByOwnerId(ownerId);

        return itemMapper.toItemDtoWithBookings(items, bookings, comments);
    }

    @Override
    public List<Item> search(String text, Integer from, Integer size) {
        return itemRepository.search(text.toLowerCase(), FromSizePageRequest.of(from, size));
    }
}
