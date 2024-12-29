package com.example.server.service.impl;

import com.example.api.dto.ItemRequestDto;
import com.example.server.repository.FromSizePageRequest;
import com.example.server.exception.NotFoundException;
import com.example.server.repository.ItemRepository;
import com.example.server.repository.UserRepository;
import com.example.server.repository.entity.Item;
import com.example.server.repository.ItemRequestRepository;
import com.example.server.mapper.ItemRequestMapper;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;
import com.example.server.service.ItemRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemRequestServiceImpl implements ItemRequestService {

    private static final Sort BY_CREATED_DESCENDING = Sort.by(Sort.Direction.DESC, "created");

    private final ItemRepository itemRepository;
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRequestMapper itemRequestMapper;
    private final UserRepository userRepository;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, Long requesterId) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден"));
        ItemRequest itemRequest = itemRequestMapper.toItemRequest(itemRequestDto, requester);

        return itemRequestMapper.toItemRequestDto(itemRequestRepository.save(itemRequest), Collections.emptyList());
    }

    @Override
    public ItemRequest findById(Long itemRequestId) {
        return itemRequestRepository.findById(itemRequestId)
                .orElseThrow(() -> new NotFoundException("Запрос на добавление вещи с указанным идентификатором не найден."));
    }

    @Override
    public ItemRequestDto findByIdWithItems(Long requestId, Long requesterId) {
        if (!userRepository.existsById(requesterId)) {
            throw new NotFoundException("Пользователь с указанным идентификатором не найден");
        }
        return itemRequestMapper.toItemRequestDto(findById(requestId), itemRepository.findAllByRequestId(requestId));
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterId(Long requesterId) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден"));
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterId(requesterId, BY_CREATED_DESCENDING);
        List<Item> items = itemRepository.findAllByRequestIdNotNull();

        return itemRequestMapper.toItemRequestDto(itemRequests, items);
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterIdNot(Long requesterId, Integer from, Integer size) {
        User requester = userRepository.findById(requesterId)
                .orElseThrow(() -> new NotFoundException("Пользователь с указанным идентификатором не найден"));
        List<ItemRequest> itemRequests = itemRequestRepository.findAllByRequesterIdNot(requesterId, FromSizePageRequest.of(from, size, BY_CREATED_DESCENDING));
        List<Item> items = itemRepository.findAllByRequestIdNotNull();

        return itemRequestMapper.toItemRequestDto(itemRequests, items);
    }
}
