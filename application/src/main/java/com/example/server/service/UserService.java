package com.example.server.service;

import com.example.server.repository.entity.User;

import java.util.List;

public interface UserService {

    User create(User user);

    User update(User user);

    User findById(Long id);

    List<User> findAll();

    void deleteById(Long id);
}
