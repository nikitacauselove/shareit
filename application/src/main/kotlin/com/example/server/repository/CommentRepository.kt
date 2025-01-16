package com.example.server.repository

import com.example.server.repository.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

/**
 * Репозиторий для взаимодействия с отзывами.
 */
@Repository
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
    @Query(FIND_ALL_BY_OWNER_ID)
    fun findAllByOwnerId(@Param("ownerId") ownerId: Long): List<Comment>

    companion object {
        const val FIND_ALL_BY_OWNER_ID: String = """
            SELECT comment
            FROM Comment AS comment
            JOIN FETCH comment.item AS item
            JOIN FETCH item.owner AS user
            WHERE user.id = :ownerId
            """
    }
}
