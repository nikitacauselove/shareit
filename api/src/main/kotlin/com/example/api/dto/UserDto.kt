package com.example.api.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "Информация о пользователе")
data class UserDto(
    @get:Schema(description = "Идентификатор пользователя") val id: Long,
    @get:Schema(description = "Имя пользователя") @get:NotBlank(groups = [OnCreate::class]) val name: String?,
    @get:Schema(description = "Электронная почта пользователя") @get:Email(groups = [OnCreate::class, OnUpdate::class]) @get:NotNull(groups = [OnCreate::class]) val email: String?
)
