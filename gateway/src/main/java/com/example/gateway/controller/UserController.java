package com.example.gateway.controller;

import com.example.api.UserApi;
import com.example.api.dto.UserDto;
import com.example.gateway.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = UserApi.PATH)
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserClient userClient;

    @Override
    public UserDto create(UserDto userDto) {
        return userClient.create(userDto);
    }

    @Override
    public UserDto update(Long id, UserDto userDto) {
        return userClient.update(id, userDto);
    }

    @Override
    public UserDto findById(Long id) {
        return userClient.findById(id);
    }

    @Override
    public List<UserDto> findAll() {
        return userClient.findAll();
    }

    @Override
    public Map<String, String> deleteById(Long id) {
        return userClient.deleteById(id);
    }
}
