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
    public ItemDto create(ItemDto itemDto, Long userId) {
        return itemClient.create(itemDto, userId);
    }

    @Override
    public ItemDto update(Long id, ItemDto itemDto, Long userId) {
        return itemClient.update(id, itemDto, userId);
    }

    @Override
    public ItemDtoWithBookings findById(Long id, Long userId) {
        return itemClient.findById(id, userId);
    }

    @Override
    public List<ItemDtoWithBookings> findAllByOwnerId(Long userId, Integer from, Integer size) {
        return itemClient.findAllByOwnerId(userId, from, size);
    }

    @Override
    public List<ItemDto> search(String text, Integer from, Integer size) {
        return itemClient.search(text, from, size);
    }
}
