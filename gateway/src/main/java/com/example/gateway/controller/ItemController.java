package com.example.gateway.controller;

import com.example.api.ItemApi;
import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.gateway.client.ItemClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = ItemApi.PATH)
@RequiredArgsConstructor
public class ItemController implements ItemApi {

    private final ItemClient itemClient;

    @Override
    public ItemDto create(ItemDto itemDto, Long ownerId) {
        return itemClient.create(itemDto, ownerId);
    }

    @Override
    public ItemDto update(Long itemId, ItemDto itemDto, Long ownerId) {
        return itemClient.update(itemId, itemDto, ownerId);
    }

    @Override
    public ItemDtoWithBookings findById(Long itemId, Long userId) {
        return itemClient.findById(itemId, userId);
    }

    @Override
    public List<ItemDtoWithBookings> findAllByOwnerId(Long ownerId, Integer from, Integer size) {
        return itemClient.findAllByOwnerId(ownerId, from, size);
    }

    @Override
    public List<ItemDto> search(String text, Integer from, Integer size) {
        return itemClient.search(text, from, size);
    }
}
