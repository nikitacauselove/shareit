package com.example.server.controller;

import com.example.server.service.ItemRequestService;
import com.example.server.dto.ItemRequestDto;
import com.example.server.mapper.ItemRequestMapper;
import com.example.server.repository.entity.ItemRequest;
import com.example.server.service.UserService;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.server.controller.UserController.X_SHARER_USER_ID;

@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemRequestController {
    private final ItemRequestService itemRequestService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ItemRequestDto> create(@RequestBody ItemRequestDto itemRequestDto, @RequestHeader(X_SHARER_USER_ID) long requesterId) {
        ItemRequest itemRequest = ItemRequestMapper.toItemRequest(itemRequestDto, userService.findById(requesterId));

        log.info("Добавление нового запроса вещи пользователем с идентификатором {}.", requesterId);
        return ResponseEntity.ok(itemRequestService.create(itemRequest));
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<ItemRequestDto> findById(@PathVariable long requestId, @RequestHeader(X_SHARER_USER_ID) long requesterId) {
        User requester = userService.findById(requesterId);

        log.info("Получение данных об одном конкретном запросе с идентификатором {} вместе с данными об ответах на него пользователем с идентификатором {}.", requestId, requesterId);
        return ResponseEntity.ok(itemRequestService.findByIdWithItems(requestId));
    }

    @GetMapping
    public ResponseEntity<List<ItemRequestDto>> findAllByRequesterId(@RequestHeader(X_SHARER_USER_ID) long requesterId) {
        User requester = userService.findById(requesterId);

        log.info("Получение списка своих запросов пользователем с идентификатором {} вместе с данными об ответах на них.", requesterId);
        return ResponseEntity.ok(itemRequestService.findAllByRequesterId(requester.getId()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ItemRequestDto>> findAllByRequesterIdNot(@RequestHeader(X_SHARER_USER_ID) long requesterId, @RequestParam int from, @RequestParam int size) {
        User requester = userService.findById(requesterId);

        log.info("Получение списка запросов, созданных другими пользователями (не пользователем с идентификаторос {}).", requesterId);
        return ResponseEntity.ok(itemRequestService.findAllByRequesterIdNot(requester.getId(), from, size));
    }
}
