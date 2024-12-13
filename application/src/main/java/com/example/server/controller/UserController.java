package com.example.server.controller;

import com.example.api.UserApi;
import com.example.api.dto.UserDto;
import com.example.server.service.UserService;
import com.example.server.mapper.UserMapper;
import com.example.server.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = UserApi.PATH)
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserMapper userMapper;
    private final UserService userService;

    @Override
    public UserDto create(UserDto userDto) {
        User user = userMapper.toUser(userDto);

        return userMapper.toUserDto(userService.create(user));
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        User user = userMapper.updateUser(userDto, userService.findById(id));

        return userMapper.toUserDto(userService.update(user));
    }

    @Override
    public UserDto findById(Long id) {
        return userMapper.toUserDto(userService.findById(id));
    }

    @Override
    public List<UserDto> findAll() {
        return userMapper.toUserDto(userService.findAll());
    }

    @Override
    public Map<String, String> deleteById(Long id) {
        userService.deleteById(id);
        return Map.of("message", "Пользователь с указанным идентификатором был успешно удален.");
    }
}
