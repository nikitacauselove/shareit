package com.example.api;

import com.example.api.dto.ItemDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import static com.example.api.Constant.FROM_ERROR_MESSAGE;
import static com.example.api.Constant.SIZE_ERROR_MESSAGE;
import static com.example.api.Constant.X_SHARER_USER_ID;

@RequestMapping(path = "/items")
public interface ItemApi {

    @PostMapping
    ResponseEntity<Object> create(@RequestBody @Valid ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) long ownerId);

    @PatchMapping("/{itemId}")
    ResponseEntity<Object> update(@PathVariable long itemId, @RequestBody ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) long ownerId);

    @GetMapping("/{itemId}")
    ResponseEntity<Object> findById(@PathVariable long itemId, @RequestHeader(X_SHARER_USER_ID) long userId);

    @GetMapping
    ResponseEntity<Object> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) long ownerId,
                                            @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) int from,
                                            @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) int size);

    @GetMapping("/search")
    ResponseEntity<Object> search(@RequestParam String text,
                                  @RequestParam(defaultValue = "0") @PositiveOrZero(message = FROM_ERROR_MESSAGE) int from,
                                  @RequestParam(defaultValue = "10") @Positive(message = SIZE_ERROR_MESSAGE) int size);
}
