package com.example.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record UserDto(Long id,
                      @NotBlank(groups = {OnCreate.class}, message = "Имя пользователя не может быть пустым.") String name,
                      @Email(groups = {OnCreate.class, OnUpdate.class}, message = "Некорректный электронный адрес пользователя.")
                      @NotNull(groups = {OnCreate.class}, message = "Электронный адрес пользователя не может быть пустым.") String email) {
}
