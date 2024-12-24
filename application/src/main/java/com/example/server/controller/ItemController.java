package com.example.server.controller;

import com.example.api.ItemApi;
import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.service.ItemService;
import com.example.server.mapper.ItemMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ItemDto update(Long id, ItemDto itemDto, Long userId) {
        return itemMapper.toItemDto(itemService.update(id, itemDto, userId));
    }

    @Override
    public ItemDtoWithBookings findById(Long id, Long userId) {
        return itemService.findByIdWithBooking(id, userId);
    }

    @Override
    public List<ItemDtoWithBookings> findAllByOwnerId(Long userId, Integer from, Integer size) {
        return itemService.findAllByOwnerId(userId, from, size);
    }

    @Override
    public List<ItemDto> search(String text, Integer from, Integer size) {
        return itemMapper.toItemDto(itemService.search(text, from, size));
    }
}
