package com.example.api;

import com.example.api.dto.OnCreate;
import com.example.api.dto.OnUpdate;
import com.example.api.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Tag(name = "Пользователи", description = "Взаимодействие с пользователями")
@Validated
public interface UserApi {

    String PATH = "v1/users";

    String X_SHARER_USER_ID = "X-Sharer-User-Id";

    @PostMapping
    @Operation(description = "Добавление нового пользователя")
    UserDto create(@RequestBody @Validated(OnCreate.class) UserDto userDto);

    @PatchMapping("/{id}")
    @Operation(description = "Обновление информации о пользователе")
    UserDto update(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) UserDto userDto);

    @GetMapping("/{id}")
    @Operation(description = "Получение информации о пользователе")
    UserDto findById(@PathVariable Long id);

    @GetMapping
    @Operation(description = "Получение списка всех пользователей")
    List<UserDto> findAll();

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление пользователя")
    Map<String, String> deleteById(@PathVariable Long id);
}
