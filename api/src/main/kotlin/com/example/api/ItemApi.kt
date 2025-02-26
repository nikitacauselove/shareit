package com.example.api

import com.example.api.model.ItemDto
import com.example.api.model.ItemDtoWithBooking
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Предметы", description = "Взаимодействие с предметами")
@Validated
interface ItemApi {

    @PostMapping
    @Operation(description = "Добавление нового предмета")
    fun create(@RequestBody @Valid itemDto: ItemDto, @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long): ItemDto

    @PatchMapping("/{id}")
    @Operation(description = "Обновление информации о предмете")
    fun update(@PathVariable id: Long, @RequestBody itemDto: ItemDto, @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long): ItemDto

    @GetMapping("/{id}")
    @Operation(description = "Получение информации о предмете")
    fun findById(@PathVariable id: Long, @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long): ItemDtoWithBooking

    @GetMapping
    @Operation(description = "Получение владельцем списка всех его предметов")
    fun findAllByOwnerId(
        @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long,
        @Parameter(description = "Индекс первого элемента") @RequestParam(defaultValue = "0") @PositiveOrZero from: Int,
        @Parameter(description = "Количество элементов для отображения") @RequestParam(defaultValue = "10") @Positive size: Int
    ): List<ItemDtoWithBooking>

    @GetMapping("/search")
    @Operation(description = "Поиск предметов")
    fun search(
        @Parameter(description = "Текст для поиска предметов") @RequestParam text: String,
        @Parameter(description = "Индекс первого элемента") @RequestParam(defaultValue = "0") @PositiveOrZero from: Int,
        @Parameter(description = "Количество элементов для отображения") @RequestParam(defaultValue = "10") @Positive size: Int
    ): List<ItemDto>

    companion object {
        const val PATH = "v1/items"
    }
}
