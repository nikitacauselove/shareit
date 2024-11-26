package com.example.server.controller;

import com.example.api.UserApi;
import com.example.api.dto.UserDto;
import com.example.server.service.UserService;
import com.example.server.mapper.UserMapper;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = UserApi.PATH)
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;
    private final UserMapper userMapper;

    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.toUser(userDto);

        log.info("Добавление нового пользователя.");
        return userMapper.toUserDto(userService.create(user));
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = userMapper.toUser(userService.findById(id), userDto);

        log.info("Редактирование пользователя с идентификатором {}.", id);
        return userMapper.toUserDto(userService.update(user));
    }

    @Override
    public UserDto findById(Long id) {
        log.info("Просмотр информации о конкретном пользователе с идентификатором {}.", id);
        return userMapper.toUserDto(userService.findById(id));
    }

    @Override
    public List<UserDto> findAll() {
        log.info("Просмотр списка всех пользователей.");
        return userMapper.toUserDto(userService.findAll());
    }

    @Override
    public Map<String, String> deleteById(Long id) {
        log.info("Удаление пользователя с идентификатором {}.", id);
        userService.deleteById(id);
        return Map.of("message", "Пользователь с указанным идентификатором был успешно удален.");
    }
}
