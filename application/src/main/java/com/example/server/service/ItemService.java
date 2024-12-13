package com.example.server.service;

import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.repository.entity.Item;

import java.util.List;

public interface ItemService {

    Item create(Item item);

    Item update(Item item);

    Item findById(long itemId);

    ItemDtoWithBookings findByIdWithBooking(long itemId, long userId);

    List<ItemDtoWithBookings> findAllByOwnerId(long ownerId, int from, int size);

    List<Item> search(String text, int from, int size);
}
