package com.example.server.controller;

import com.example.api.ItemRequestApi;
import com.example.api.dto.ItemRequestDto;
import com.example.server.service.ItemRequestService;
import com.example.server.mapper.ItemRequestMapper;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.service.UserService;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = ItemRequestApi.PATH)
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController implements ItemRequestApi {

    private final ItemRequestService itemRequestService;
    private final ItemRequestMapper itemRequestMapper;
    private final UserService userService;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, Long requesterId) {
        ItemRequest itemRequest = itemRequestMapper.toItemRequest(itemRequestDto, userService.findById(requesterId));

        log.info("Добавление нового запроса вещи пользователем с идентификатором {}.", requesterId);
        return itemRequestService.create(itemRequest);
    }

    @Override
    public ItemRequestDto findById(Long requestId, Long requesterId) {
        User requester = userService.findById(requesterId);

        log.info("Получение данных об одном конкретном запросе с идентификатором {} вместе с данными об ответах на него пользователем с идентификатором {}.", requestId, requesterId);
        return itemRequestService.findByIdWithItems(requestId);
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterId(Long requesterId) {
        User requester = userService.findById(requesterId);

        log.info("Получение списка своих запросов пользователем с идентификатором {} вместе с данными об ответах на них.", requesterId);
        return itemRequestService.findAllByRequesterId(requester.getId());
    }

    @Override
    public List<ItemRequestDto> findAllByRequesterIdNot(Long requesterId, Integer from, Integer size) {
        User requester = userService.findById(requesterId);

        log.info("Получение списка запросов, созданных другими пользователями (не пользователем с идентификаторос {}).", requesterId);
        return itemRequestService.findAllByRequesterIdNot(requester.getId(), from, size);
    }
}
