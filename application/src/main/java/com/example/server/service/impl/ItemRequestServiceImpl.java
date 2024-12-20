package com.example.server.service.impl;

import com.example.api.dto.ItemRequestDto;
import com.example.server.repository.FromSizePageRequest;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.entity.Item;
import com.example.server.repository.ItemRequestRepository;
import com.example.server.mapper.ItemRequestMapper;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.service.ItemRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemRequestServiceImpl implements ItemRequestService {

    private static final Sort BY_CREATED_DESCENDING = Sort.by(Sort.Direction.DESC, "created");

    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRequestMapper itemRequestMapper;

    @Override
    public ItemRequestDto create(ItemRequest itemRequest) {
        return itemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest), Collections.emptyList());
    }

    @Override
    public ItemRequest findById(Long itemRequestId) {
        return itemRequestRepository.findById(itemRequestId)
                .orElseThrow(() -> new NotFoundException("Запрос на добавление вещи с указанным идентификатором не найден."));
    }

    @Override
    public ItemRequestDto findByIdWithItems(Long itemRequestId) {
        ItemRequest itemRequest = findById(itemRequestId);

        return itemRequestMapper.toItemRequestDto(itemRequest, itemRepository.findAllByRequestId(itemRequestId));
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterId(Long requesterId) {
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterId(requesterId, BY_CREATED_DESCENDING);
        List<Item> items = itemRepository.findAllByRequestIdNotNull();

        return itemRequestMapper.toItemRequestDto(itemRequests, items);
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterIdNot(Long requesterId, Integer from, Integer size) {
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterIdNot(requesterId, FromSizePageRequest.of(from, size, BY_CREATED_DESCENDING));
        List<Item> items = itemRepository.findAllByRequestIdNotNull();

        return itemRequestMapper.toItemRequestDto(itemRequests, items);
    }
}
