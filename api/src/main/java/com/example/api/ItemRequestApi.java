package com.example.api;

import com.example.api.dto.ItemRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Запросы на добавление предмета", description = "Взаимодействие с запросами на добавление предмета")
@Validated
public interface ItemRequestApi {

    String PATH = "v1/requests";

    @PostMapping
    @Operation(description = "Добавление нового запроса")
    ItemRequestDto create(@RequestBody @Valid ItemRequestDto itemRequestDto, @RequestHeader(X_SHARER_USER_ID) Long userId);

    @GetMapping("/{id}")
    @Operation(description = "Получение информации о запросе")
    ItemRequestDto findById(@PathVariable Long id, @RequestHeader(X_SHARER_USER_ID) Long userId);

    @GetMapping
    @Operation(description = "Получение списка всех запросов, добавленных пользователем")
    List<ItemRequestDto> findAllByRequesterId(@RequestHeader(X_SHARER_USER_ID) Long userId);

    @GetMapping("/all")
    @Operation(description = "Получение списка всех запросов, добавленных другими пользователями")
    List<ItemRequestDto> findAllByRequesterIdNot(@RequestHeader(X_SHARER_USER_ID) Long userId,
                                                 @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(defaultValue = "10") @Positive Integer size);
}
