package com.example.server.service;

import com.example.api.dto.ItemRequestDto;
import com.example.server.repository.entity.ItemRequest;

import java.util.List;

public interface ItemRequestService {

    ItemRequestDto create(ItemRequest itemRequest);

    ItemRequest findById(Long itemRequestId);

    ItemRequestDto findByIdWithItems(Long itemRequestId);

    List<ItemRequestDto> findAllByRequesterId(Long requesterId);

    List<ItemRequestDto> findAllByRequesterIdNot(Long requesterId, Integer from, Integer size);
}
