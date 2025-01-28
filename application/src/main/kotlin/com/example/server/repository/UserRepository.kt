package com.example.server.repository

import com.example.server.repository.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Репозиторий для взаимодействия с пользователями.
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {

    /**
     * Существует ли пользователь с указанным адресом электронной почты.
     * @param id идентификатор пользователя
     * @param email электронная почта пользователя
     */
    fun existsByIdNotAndEmail(id: Long, email: String?): Boolean
}
