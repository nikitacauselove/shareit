package com.example.api

import com.example.api.group.Create
import com.example.api.group.Update
import com.example.api.dto.UserDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
@Validated
interface UserApi {

    @PostMapping
    @Operation(description = "Добавление нового пользователя")
    fun create(@RequestBody @Validated(Create::class) userDto: UserDto): UserDto

    @PatchMapping("/{id}")
    @Operation(description = "Обновление информации о пользователе")
    fun update(@PathVariable id: Long, @RequestBody @Validated(Update::class) userDto: UserDto): UserDto

    @GetMapping("/{id}")
    @Operation(description = "Получение информации о пользователе")
    fun findById(@PathVariable id: Long): UserDto

    @GetMapping
    @Operation(description = "Получение списка всех пользователей")
    fun findAll(): List<UserDto>

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление пользователя")
    fun deleteById(@PathVariable id: Long): Map<String, String>

    companion object {
        const val PATH = "v1/users"
        const val X_SHARER_USER_ID = "X-Sharer-User-Id"
    }
}
