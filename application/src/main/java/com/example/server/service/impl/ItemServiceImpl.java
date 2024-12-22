package com.example.server.service.impl;

import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.repository.FromSizePageRequest;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.BookingRepository;
import com.example.server.repository.ItemRequestRepository;
import com.example.server.repository.UserRepository;
import com.example.server.repository.entity.Booking;
import com.example.server.repository.CommentRepository;
import com.example.server.repository.entity.Comment;
import com.example.server.exception.NotFoundException;
import com.example.server.mapper.ItemMapper;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;
import com.example.server.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {

    private final BookingRepository bookingRepository;
    private final CommentRepository commentRepository;
    private final ItemMapper itemMapper;
    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Item create(ItemDto itemDto, Long userId) {
        ItemRequest itemRequest = itemDto.requestId() == null ? null : itemRequestRepository.findById(itemDto.requestId())
                .orElseThrow(() -> new NotFoundException("Запрос на добавление предмета с указанным идентификатором не найден"));
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден"));

        return itemRepository.save(itemMapper.toItem(itemDto, owner, itemRequest));
    }

    @Override
    @Transactional
    public Item update(Long itemId, ItemDto itemDto, Long ownerId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Предмет с указанным идентификатором не найден"));
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден"));

        if (findById(item.getId()).hasSameOwner(item)) {
            return itemRepository.save(itemMapper.updateItem(itemDto, owner, item));
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
