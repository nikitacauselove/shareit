package com.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDto(Long id,
                      @NotBlank(groups = {OnCreate.class}, message = "Имя пользователя не может быть пустым.") String name,
                      @Email(groups = {OnCreate.class, OnUpdate.class}, message = "Некорректный электронный адрес пользователя.")
                      @NotNull(groups = {OnCreate.class}, message = "Электронный адрес пользователя не может быть пустым.") String email) {
}
