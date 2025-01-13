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

import java.util.Collections;
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
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден"));
        ItemRequest itemRequest = itemDto.getRequestId() == null ? null : itemRequestRepository.findById(itemDto.getRequestId())
                .orElseThrow(() -> new NotFoundException("Запрос на добавление предмета с указанным идентификатором не найден"));

        return itemRepository.save(itemMapper.toItem(itemDto, owner, itemRequest));
    }

    @Override
    @Transactional
    public Item update(Long id, ItemDto itemDto, Long userId) {
        Item item = findById(id);
        Long ownerId = item.getOwner().getId();

        if (!ownerId.equals(userId)) {
            throw new NotFoundException("Обновлять информацию о предмете может только его владелец");
        }
        return itemRepository.save(itemMapper.updateItem(itemDto, item));
    }

    @Override
    public Item findById(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Предмет с указанным идентификатором не найден"));
    }

    @Override
    public ItemDtoWithBookings findByIdWithBooking(Long id, Long userId) {
        Item item = findById(id);
        Long ownerId = item.getOwner().getId();

        if (ownerId.equals(userId)) {
            return itemMapper.toItemDtoWithBookings(item, bookingRepository.findAllByItemId(id), commentRepository.findAllByItemId(id));
        }
        return itemMapper.toItemDtoWithBookings(item, commentRepository.findAllByItemId(id));
    }

    @Override
    public List<ItemDtoWithBookings> findAllByOwnerId(Long userId, Integer from, Integer size) {
        List<Item> items = itemRepository.findAllByOwnerId(userId, FromSizePageRequest.of(from, size, Sort.by(Sort.Direction.ASC, Item.Fields.id)));
        List<Booking> bookings = bookingRepository.findAllByItem_Owner_Id(userId, Pageable.unpaged());
        List<Comment> comments = commentRepository.findAllByOwnerId(userId);

        return itemMapper.toItemDtoWithBookings(items, bookings, comments);
    }

    @Override
    public List<Item> search(String text, Integer from, Integer size) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemRepository.search(text.toLowerCase(), FromSizePageRequest.of(from, size));
    }
}
