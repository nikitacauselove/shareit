package com.example.gateway.controller;

import com.example.api.RequestApi;
import com.example.api.dto.ItemRequestDto;
import com.example.gateway.client.RequestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = RequestApi.PATH)
@RequiredArgsConstructor
public class RequestController implements RequestApi {

    private final RequestClient requestClient;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, Long requesterId) {
        return requestClient.create(itemRequestDto, requesterId);
    }

    @Override
    public ItemRequestDto findById(Long requestId, Long requesterId) {
        return requestClient.findById(requestId, requesterId);
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterId(Long requesterId) {
        return requestClient.findAllByRequesterId(requesterId);
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterIdNot(Long requesterId, Integer from, Integer size) {
        return requestClient.findAllByRequesterIdNot(requesterId, from, size);
    }
}
