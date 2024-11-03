package com.example.server.service;

import com.example.api.dto.ItemRequestDto;
import com.example.server.FromSizePageRequest;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.entity.Item;
import com.example.server.repository.ItemRequestRepository;
import com.example.server.mapper.ItemRequestMapper;
import com.example.server.repository.entity.ItemRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ItemRequestService {
    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;

    private static final Sort BY_CREATED_DESCENDING = Sort.by(Sort.Direction.DESC, "created");

    @Transactional
    public ItemRequestDto create(ItemRequest itemRequest) {
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest), Collections.emptyList());
    }

    public ItemRequest findById(long itemRequestId) {
        return itemRequestRepository.findById(itemRequestId).orElseThrow(() -> new NotFoundException("Запрос на добавление вещи с указанным идентификатором не найден."));
    }

    public ItemRequestDto findByIdWithItems(long itemRequestId) {
        ItemRequest itemRequest = findById(itemRequestId);

        return ItemRequestMapper.toItemRequestDto(itemRequest, itemRepository.findAllByRequestId(itemRequestId));
    }

    public List<ItemRequestDto> findAllByRequesterId(long requesterId) {
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterId(requesterId, BY_CREATED_DESCENDING);
        List<Item> items = itemRepository.findAllByRequestIdNotNull();

        return ItemRequestMapper.toItemRequestDto(itemRequests, items);
    }

    public List<ItemRequestDto> findAllByRequesterIdNot(long requesterId, int from, int size) {
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterIdNot(requesterId, FromSizePageRequest.of(from, size, BY_CREATED_DESCENDING));
        List<Item> items = itemRepository.findAllByRequestIdNotNull();

        return ItemRequestMapper.toItemRequestDto(itemRequests, items);
    }
}
