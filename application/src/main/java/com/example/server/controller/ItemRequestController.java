package com.example.server.controller;

import com.example.api.ItemRequestApi;
import com.example.api.dto.ItemRequestDto;
import com.example.server.service.ItemRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = ItemRequestApi.PATH)
@RequiredArgsConstructor
public class ItemRequestController implements ItemRequestApi {

    private final ItemRequestService itemRequestService;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, Long userId) {
        return itemRequestService.create(itemRequestDto, userId);
    }

    @Override
    public ItemRequestDto findById(Long id, Long userId) {
        return itemRequestService.findByIdWithItems(id, userId);
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterId(Long userId) {
        return itemRequestService.findAllByRequesterId(userId);
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterIdNot(Long userId, Integer from, Integer size) {
        return itemRequestService.findAllByRequesterIdNot(userId, from, size);
    }
}
