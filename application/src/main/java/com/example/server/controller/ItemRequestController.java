package com.example.server.controller;

import com.example.api.ItemRequestApi;
import com.example.api.dto.ItemRequestDto;
import com.example.server.service.ItemRequestService;
import com.example.server.service.UserService;
import com.example.server.mapper.ItemRequestMapper;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = ItemRequestApi.PATH)
@RequiredArgsConstructor
public class ItemRequestController implements ItemRequestApi {

    private final ItemRequestService itemRequestService;
    private final ItemRequestMapper itemRequestMapper;
    private final UserService userService;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, Long requesterId) {
        ItemRequest itemRequest = itemRequestMapper.toItemRequest(itemRequestDto, userService.findById(requesterId));

        return itemRequestService.create(itemRequest);
    }

    @Override
    public ItemRequestDto findById(Long requestId, Long requesterId) {
        User requester = userService.findById(requesterId);

        return itemRequestService.findByIdWithItems(requestId);
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterId(Long requesterId) {
        User requester = userService.findById(requesterId);

        return itemRequestService.findAllByRequesterId(requester.getId());
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterIdNot(Long requesterId, Integer from, Integer size) {
        User requester = userService.findById(requesterId);

        return itemRequestService.findAllByRequesterIdNot(requester.getId(), from, size);
    }
}
