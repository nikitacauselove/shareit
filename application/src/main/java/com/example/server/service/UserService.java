package com.example.server.service;

import com.example.api.dto.UserDto;
import com.example.server.repository.entity.User;

import java.util.List;

/**
 * Сервис для взаимодействия с пользователями.
 */
public interface UserService {

    /**
     * Добавление нового пользователя.
     * @param user информация о пользователе
     */
    User create(User user);

    /**
     * Обновление информации о пользователе.
     * @param id идентификатор пользователя
     * @param userDto информация о пользователе
     */
    User update(Long id, UserDto userDto);

    /**
     * Получение информации о пользователе.
     * @param id идентификатор пользователя
     */
    User findById(Long id);

    /**
     * Получение списка всех пользователей.
     */
    List<User> findAll();

    /**
     * Удаление пользователя.
     * @param id идентификатор пользователя
     */
    void deleteById(Long id);
}
