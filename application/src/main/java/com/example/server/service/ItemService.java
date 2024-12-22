package com.example.server.service;

import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.repository.entity.Item;

import java.util.List;

public interface ItemService {

    Item create(ItemDto itemDto, Long userId);

    Item update(Long itemId, ItemDto itemDto, Long ownerId);

    Item findById(Long itemId);

    ItemDtoWithBookings findByIdWithBooking(Long itemId, Long userId);

    List<ItemDtoWithBookings> findAllByOwnerId(Long ownerId, Integer from, Integer size);

    List<Item> search(String text, Integer from, Integer size);
}
