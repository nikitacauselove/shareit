package com.example.server.controller;

import com.example.api.ItemApi;
import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.service.ItemService;
import com.example.server.mapper.ItemMapper;
import com.example.server.repository.entity.Item;
import com.example.server.service.ItemRequestService;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(path = ItemApi.PATH)
@RequiredArgsConstructor
@Slf4j
public class ItemController implements ItemApi {

    private final ItemRequestService itemRequestService;
    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final UserService userService;

    @Override
    public ItemDto create(ItemDto itemDto, Long ownerId) {
        ItemRequest itemRequest = itemDto.requestId() == null ? null : itemRequestService.findById(itemDto.requestId());
        Item item = itemMapper.toItem(itemDto, userService.findById(ownerId), itemRequest);

        log.info("Добавление новой вещи пользователем с идентификатором {}.", ownerId);
        return itemMapper.toItemDto(itemService.create(item));
    }

    @Override
    public ItemDto update(Long itemId, ItemDto itemDto, Long ownerId) {
        Item item = itemMapper.toItem(itemService.findById(itemId), itemDto, userService.findById(ownerId));

        log.info("Редактирование вещи с идентификатором {} пользователем с идентификатором {}.", itemId, ownerId);
        return itemMapper.toItemDto(itemService.update(item));
    }

    @Override
    public ItemDtoWithBookings findById(Long itemId, Long userId) {
        log.info("Просмотр информации о конкретной вещи с идентификатором {} пользователем с идентификатором {}.", itemId, userId);
        return itemService.findByIdWithBooking(itemId, userId);
    }

    @Override
    public List<ItemDtoWithBookings> findAllByOwnerId(Long ownerId, Integer from, Integer size) {
        log.info("Просмотр пользователем с идентификатором {} списка всех его вещей с указанием названия и описания для каждой.", ownerId);
        return itemService.findAllByOwnerId(ownerId, from, size);
    }

    @Override
    public List<ItemDto> search(String text, Integer from, Integer size) {
        log.info("Поиск вещи потенциальным арендатором. Пользователь передаёт в строке запроса текст: {}.", text);
        if (text.isBlank()) {
            return Collections.emptyList();
        }
        return itemMapper.toItemDto(itemService.search(text, from, size));
    }
}
