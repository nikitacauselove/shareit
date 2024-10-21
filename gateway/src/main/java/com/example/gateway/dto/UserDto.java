package com.example.gateway.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
    private final Long id;

    @NotBlank(groups = {OnCreate.class}, message = "Имя пользователя не может быть пустым.")
    private final String name;

    @Email(groups = {OnCreate.class, OnUpdate.class}, message = "Некорректный электронный адрес пользователя.")
    @NotNull(groups = {OnCreate.class}, message = "Электронный адрес пользователя не может быть пустым.")
    private final String email;
}
