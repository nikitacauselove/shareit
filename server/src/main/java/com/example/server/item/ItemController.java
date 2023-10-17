package com.example.server.item;

import com.example.server.item.dto.ItemDto;
import com.example.server.item.dto.ItemDtoWithBookings;
import com.example.server.item.dto.ItemMapper;
import com.example.server.item.model.Item;
import com.example.server.request.ItemRequestService;
import com.example.server.request.model.ItemRequest;
import com.example.server.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.example.server.user.UserController.X_SHARER_USER_ID;


@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated
public class ItemController {
    private final ItemRequestService itemRequestService;
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ItemDto> create(@RequestBody ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) long ownerId) {
        ItemRequest itemRequest = itemDto.getRequestId() == null ? null : itemRequestService.findById(itemDto.getRequestId());
        Item item = ItemMapper.toItem(itemDto, userService.findById(ownerId), itemRequest);

        log.info("Добавление новой вещи пользователем с идентификатором {}.", ownerId);
        return ResponseEntity.ok(ItemMapper.toItemDto(itemService.create(item)));
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<ItemDto> update(@PathVariable long itemId, @RequestBody ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) long ownerId) {
        Item item = ItemMapper.toItem(itemService.findById(itemId), itemDto, userService.findById(ownerId));

        log.info("Редактирование вещи с идентификатором {} пользователем с идентификатором {}.", itemId, ownerId);
        return ResponseEntity.ok(ItemMapper.toItemDto(itemService.update(item)));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemDtoWithBookings> findById(@PathVariable long itemId, @RequestHeader(X_SHARER_USER_ID) long userId) {
        log.info("Просмотр информации о конкретной вещи с идентификатором {} пользователем с идентификатором {}.", itemId, userId);
        return ResponseEntity.ok(itemService.findByIdWithBooking(itemId, userId));
    }

    @GetMapping
    public ResponseEntity<List<ItemDtoWithBookings>> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) long ownerId, @RequestParam int from, @RequestParam int size) {
        log.info("Просмотр пользователем с идентификатором {} списка всех его вещей с указанием названия и описания для каждой.", ownerId);
        return ResponseEntity.ok(itemService.findAllByOwnerId(ownerId, from, size));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ItemDto>> search(@RequestParam String text, @RequestParam int from, @RequestParam int size) {
        log.info("Поиск вещи потенциальным арендатором. Пользователь передаёт в строке запроса текст: {}.", text);
        if (text.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(ItemMapper.toItemDto(itemService.search(text, from, size)));
    }
}
