package com.example.server.controller;

import com.example.api.ItemApi;
import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.service.ItemService;
import com.example.server.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = ItemApi.PATH)
@RequiredArgsConstructor
public class ItemController implements ItemApi {

    private final ItemMapper itemMapper;
    private final ItemService itemService;

    @Override
    public ItemDto create(ItemDto itemDto, Long userId) {
        return itemMapper.toItemDto(itemService.create(itemDto, userId));
    }

    @Override
    public ItemDto update(Long itemId, ItemDto itemDto, Long ownerId) {
        return itemMapper.toItemDto(itemService.update(itemId, itemDto, ownerId));
    }

    @Override
    public ItemDtoWithBookings findById(Long itemId, Long userId) {
        return itemService.findByIdWithBooking(itemId, userId);
    }

    @Override
    public List<ItemDtoWithBookings> findAllByOwnerId(Long ownerId, Integer from, Integer size) {
        return itemService.findAllByOwnerId(ownerId, from, size);
    }

    @Override
    public List<ItemDto> search(String text, Integer from, Integer size) {
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemMapper.toItemDto(itemService.search(text, from, size));
    }
}
