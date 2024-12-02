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
public class ItemRequestService {

    private static final Sort BY_CREATED_DESCENDING = Sort.by(Sort.Direction.DESC, "created");

    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRequestMapper itemRequestMapper;

    @Transactional
    public ItemRequestDto create(ItemRequest itemRequest) {
        return itemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest), Collections.emptyList());
    }

    public ItemRequest findById(Long itemRequestId) {
        return itemRequestRepository.findById(itemRequestId)
                .orElseThrow(() -> new NotFoundException("Запрос на добавление вещи с указанным идентификатором не найден."));
    }

    public ItemRequestDto findByIdWithItems(Long itemRequestId) {
        ItemRequest itemRequest = findById(itemRequestId);

        return itemRequestMapper.toItemRequestDto(itemRequest, itemRepository.findAllByRequestId(itemRequestId));
    }

    public List<ItemRequestDto> findAllByRequesterId(Long requesterId) {
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterId(requesterId, BY_CREATED_DESCENDING);
        List<Item> items = itemRepository.findAllByRequestIdNotNull();

        return itemRequestMapper.toItemRequestDto(itemRequests, items);
    }

    public List<ItemRequestDto> findAllByRequesterIdNot(Long requesterId, Integer from, Integer size) {
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterIdNot(requesterId, FromSizePageRequest.of(from, size, BY_CREATED_DESCENDING));
        List<Item> items = itemRepository.findAllByRequestIdNotNull();

        return itemRequestMapper.toItemRequestDto(itemRequests, items);
    }
}
