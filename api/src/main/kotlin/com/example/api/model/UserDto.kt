package com.example.api.model

import com.example.api.group.Create
import com.example.api.group.Update
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@Schema(description = "Информация о пользователе")
data class UserDto(

    @get:Schema(description = "Идентификатор пользователя")
    val id: Long,

    @get:Schema(description = "Имя пользователя")
    @get:NotBlank(groups = [Create::class])
    val name: String?,

    @get:Schema(description = "Электронная почта пользователя")
    @get:Email(groups = [Create::class, Update::class])
    @get:NotNull(groups = [Create::class])
    val email: String?
)
