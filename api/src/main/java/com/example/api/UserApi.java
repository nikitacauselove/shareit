package com.example.api;

import com.example.api.dto.OnCreate;
import com.example.api.dto.OnUpdate;
import com.example.api.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "/users")
@Validated
public interface UserApi {

    @PostMapping
    ResponseEntity<Object> create(@RequestBody @Validated(OnCreate.class) UserDto userDto);

    @PatchMapping("/{id}")
    ResponseEntity<Object> update(@PathVariable long id, @RequestBody @Validated(OnUpdate.class) UserDto userDto);

    @GetMapping("/{id}")
    ResponseEntity<Object> findById(@PathVariable long id);

    @GetMapping
    ResponseEntity<Object> findAll();

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteById(@PathVariable long id);
}
