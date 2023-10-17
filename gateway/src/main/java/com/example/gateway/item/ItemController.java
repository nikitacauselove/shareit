package com.example.gateway.item;

import com.example.gateway.item.dto.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Collections;

import static com.example.gateway.exception.GlobalControllerExceptionHandler.FROM_ERROR_MESSAGE;
import static com.example.gateway.exception.GlobalControllerExceptionHandler.SIZE_ERROR_MESSAGE;
import static com.example.gateway.user.UserController.X_SHARER_USER_ID;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) long ownerId) {
        return itemClient.create(itemDto, ownerId);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@PathVariable long itemId, @RequestBody ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) long ownerId) {
        return itemClient.update(itemId, itemDto, ownerId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> findById(@PathVariable long itemId, @RequestHeader(X_SHARER_USER_ID) long userId) {
        return itemClient.findById(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) long ownerId,
                                                   @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) int from,
                                                   @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) int size) {
        return itemClient.findAllByOwnerId(ownerId, from, size);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestParam String text,
                                         @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) int from,
                                         @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) int size) {
        if (text.isBlank()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return itemClient.search(text, from, size);
    }
}
