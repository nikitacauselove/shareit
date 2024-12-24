package com.example.api;

import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Предметы", description = "Взаимодействие с предметами")
@Validated
public interface ItemApi {

    String PATH = "v1/items";

    @PostMapping
    @Operation(description = "Добавление нового предмета")
    ItemDto create(@RequestBody @Valid ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) Long userId);

    @PatchMapping("/{id}")
    @Operation(description = "Обновление информации о предмете")
    ItemDto update(@PathVariable Long id, @RequestBody ItemDto itemDto, @RequestHeader(X_SHARER_USER_ID) Long userId);

    @GetMapping("/{id}")
    @Operation(description = "Получение информации о предмете")
    ItemDtoWithBookings findById(@PathVariable Long id, @RequestHeader(X_SHARER_USER_ID) Long userId);

    @GetMapping
    @Operation(description = "Получение владельцем списка всех его предметов")
    List<ItemDtoWithBookings> findAllByOwnerId(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                               @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                               @RequestParam(defaultValue = "10") @Positive Integer size);

    @GetMapping("/search")
    @Operation(description = "Поиск предметов")
    List<ItemDto> search(@RequestParam String text,
                         @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                         @RequestParam(defaultValue = "10") @Positive Integer size);
}
