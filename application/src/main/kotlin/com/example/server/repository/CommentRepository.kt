package com.example.server.repository

import com.example.server.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Репозиторий для взаимодействия с отзывами.
 */
interface CommentRepository : JpaRepository<Comment, Long> {

    /**
     * Получение списка всех отзывов на определенный предмет.
     * @param itemId идентификатор предмета
     */
    fun findAllByItemId(itemId: Long): List<Comment>

    /**
     * Получение владельцем списка всех отзывов на его предметы.
     * @param ownerId идентификатор пользователя
     */
    @Query
    fun findAllByOwnerId(ownerId: Long): List<Comment>
}
