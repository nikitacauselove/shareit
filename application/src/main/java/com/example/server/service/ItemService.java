package com.example.server.service;

import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.repository.entity.Item;

import java.util.List;

public interface ItemService {

    Item create(Item item);

    Item update(Item item);

    Item findById(Long itemId);

    ItemDtoWithBookings findByIdWithBooking(Long itemId, Long userId);

    List<ItemDtoWithBookings> findAllByOwnerId(Long ownerId, Integer from, Integer size);

    List<Item> search(String text, Integer from, Integer size);
}
