package com.example.gateway.controller;

import com.example.api.UserApi;
import com.example.api.dto.UserDto;
import com.example.gateway.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = UserApi.PATH)
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserClient userClient;

    @Override
    public ResponseEntity<Object> create(UserDto userDto) {
        return userClient.create(userDto);
    }

    @Override
    public ResponseEntity<Object> update(long id, UserDto userDto) {
        return userClient.update(id, userDto);
    }

    @Override
    public ResponseEntity<Object> findById(long id) {
        return userClient.findById(id);
    }

    @Override
    public ResponseEntity<Object> findAll() {
        return userClient.findAll();
    }

    @Override
    public ResponseEntity<Object> deleteById(long id) {
        return userClient.deleteById(id);
    }
}
