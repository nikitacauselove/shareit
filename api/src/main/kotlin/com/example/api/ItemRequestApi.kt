package com.example.api

import com.example.api.dto.ItemRequestDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.PositiveOrZero
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Запросы на добавление предмета", description = "Взаимодействие с запросами на добавление предмета")
@Validated
interface ItemRequestApi {

    @PostMapping
    @Operation(description = "Добавление нового запроса на добавление предмета")
    fun create(
        @RequestBody @Valid itemRequestDto: ItemRequestDto,
        @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long
    ): ItemRequestDto

    @GetMapping("/{id}")
    @Operation(description = "Получение информации о запросе на добавление предмета")
    fun findById(@PathVariable id: Long, @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long): ItemRequestDto

    @GetMapping
    @Operation(description = "Получение пользователем списка всех его запросов на добавление предмета")
    fun findAllByRequesterId(@RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long): List<ItemRequestDto>

    @GetMapping("/all")
    @Operation(description = "Получение списка всех запросов на добавление предмета, добавленных другими пользователями")
    fun findAllByRequesterIdNot(
        @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long,
        @Parameter(description = "Индекс первого элемента") @RequestParam(defaultValue = "0") @PositiveOrZero from: Int,
        @Parameter(description = "Количество элементов для отображения") @RequestParam(defaultValue = "10") @Positive size: Int
    ): List<ItemRequestDto>

    companion object {
        const val PATH = "v1/requests"
    }
}
