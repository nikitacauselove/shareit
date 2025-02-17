package com.example.server.repository

import com.example.server.entity.User
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Репозиторий для взаимодействия с пользователями.
 */
interface UserRepository : JpaRepository<User, Long> {

    /**
     * Существует ли пользователь с указанным адресом электронной почты.
     *
     * @param id идентификатор пользователя
     * @param email электронная почта пользователя
     */
    fun existsByIdNotAndEmail(id: Long, email: String?): Boolean

    companion object {
        const val CONFLICT = "Пользователь с указанным адресом электронной почты уже существует"
        const val NOT_FOUND = "Пользователь с указанным идентификатором не найден"
    }
}
