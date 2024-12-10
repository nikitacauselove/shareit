package com.example.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDto(Long id,
                      @NotBlank(groups = OnCreate.class) String name,
                      @Email(groups = {OnCreate.class, OnUpdate.class}) @NotNull(groups = OnCreate.class) String email) {
}
