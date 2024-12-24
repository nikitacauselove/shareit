package com.example.gateway.controller;

import com.example.api.ItemRequestApi;
import com.example.api.dto.ItemRequestDto;
import com.example.gateway.client.ItemRequestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = ItemRequestApi.PATH)
@RequiredArgsConstructor
public class ItemRequestController implements ItemRequestApi {

    private final ItemRequestClient requestClient;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, Long userId) {
        return requestClient.create(itemRequestDto, userId);
    }

    @Override
    public ItemRequestDto findById(Long id, Long userId) {
        return requestClient.findById(id, userId);
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterId(Long userId) {
        return requestClient.findAllByRequesterId(userId);
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterIdNot(Long userId, Integer from, Integer size) {
        return requestClient.findAllByRequesterIdNot(userId, from, size);
    }
}
