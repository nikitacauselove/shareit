package com.example.server.service

import com.example.api.dto.UserDto
import com.example.server.repository.entity.User

/**
 * Сервис для взаимодействия с пользователями.
 */
interface UserService {

    /**
     * Добавление нового пользователя.
     *
     * @param user информация о пользователе
     */
    fun create(user: User): User

    /**
     * Обновление информации о пользователе.
     *
     * @param id идентификатор пользователя
     * @param userDto информация о пользователе
     */
    fun update(id: Long, userDto: UserDto): User

    /**
     * Получение информации о пользователе.
     *
     * @param id идентификатор пользователя
     */
    fun findById(id: Long): User

    /**
     * Получение списка всех пользователей.
     */
    fun findAll(): List<User>

    /**
     * Удаление пользователя.
     *
     * @param id идентификатор пользователя
     */
    fun deleteById(id: Long)
}
