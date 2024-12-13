package com.example.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Информация о пользователе")
public record UserDto(@Schema(description = "Идентификатор пользователя") Long id,
                      @Schema(description = "Имя пользователя") @NotBlank(groups = OnCreate.class) String name,
                      @Schema(description = "Электронная почта пользователя") @Email(groups = {OnCreate.class, OnUpdate.class}) @NotNull(groups = OnCreate.class) String email) {
}
