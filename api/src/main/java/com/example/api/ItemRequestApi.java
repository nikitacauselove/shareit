package com.example.api;

import com.example.api.dto.ItemRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.example.api.UserApi.X_SHARER_USER_ID;

@Validated
public interface ItemRequestApi {

    String PATH = "v1/requests";

    @PostMapping
    ItemRequestDto create(@RequestBody @Valid ItemRequestDto itemRequestDto, @RequestHeader(X_SHARER_USER_ID) Long requesterId);

    @GetMapping("/{requestId}")
    ItemRequestDto findById(@PathVariable Long requestId, @RequestHeader(X_SHARER_USER_ID) Long requesterId);

    @GetMapping
    List<ItemRequestDto> findAllByRequesterId(@RequestHeader(X_SHARER_USER_ID) Long requesterId);

    @GetMapping("/all")
    List<ItemRequestDto> findAllByRequesterIdNot(@RequestHeader(X_SHARER_USER_ID) Long requesterId,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(defaultValue = "10") @Positive Integer size);
}