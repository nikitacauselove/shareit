package com.example.api;

import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.api.UserApi.X_SHARER_USER_ID;

@Validated
public interface ItemApi {

    String PATH = "v1/items";

    @PostMapping
    ItemDto create(@RequestBody @Valid ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) Long ownerId);

    @PatchMapping("/{itemId}")
    ItemDto update(@PathVariable Long itemId, @RequestBody ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) Long ownerId);

    @GetMapping("/{itemId}")
    ItemDtoWithBookings findById(@PathVariable Long itemId, @RequestHeader(X_SHARER_USER_ID) Long userId);

    @GetMapping
    List<ItemDtoWithBookings> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) Long ownerId,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                               @RequestParam(defaultValue = "10") @Positive Integer size);

    @GetMapping("/search")
    List<ItemDto> search(@RequestParam String text,
                         @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                         @RequestParam(defaultValue = "10") @Positive Integer size);
}
