package com.example.server.controller;

import com.example.server.service.UserService;
import com.example.server.dto.UserDto;
import com.example.server.mapper.UserMapper;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    public static final String X_SHARER_USER_ID = "X-Sharer-User-Id";

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        User user = UserMapper.toUser(userDto);

        log.info("Добавление нового пользователя.");
        return ResponseEntity.ok(UserMapper.toUserDto(userService.create(user)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable long id, @RequestBody UserDto userDto) {
        User user = UserMapper.toUser(userService.findById(id), userDto);

        log.info("Редактирование пользователя с идентификатором {}.", id);
        return ResponseEntity.ok(UserMapper.toUserDto(userService.update(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable long id) {
        log.info("Просмотр информации о конкретном пользователе с идентификатором {}.", id);
        return ResponseEntity.ok(UserMapper.toUserDto(userService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        log.info("Просмотр списка всех пользователей.");
        return ResponseEntity.ok(UserMapper.toUserDto(userService.findAll()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteById(@PathVariable long id) {
        log.info("Удаление пользователя с идентификатором {}.", id);
        userService.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Пользователь с указанным идентификатором был успешно удален."));
    }
}
