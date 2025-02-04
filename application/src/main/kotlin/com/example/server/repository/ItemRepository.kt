package com.example.server.repository

import com.example.server.repository.entity.Item
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

/**
 * Репозиторий для взаимодействия с предметами.
 */
@Repository
interface ItemRepository : JpaRepository<Item, Long> {

    /**
     * Получение владельцем списка всех его предметов.
     * @param ownerId идентификатор пользователя
     */
    fun findAllByOwnerId(ownerId: Long, pageable: Pageable): List<Item>

    /**
     * Поиск предметов.
     * @param text текст для поиска
     */
    @Query(SEARCH)
    fun search(text: String, pageable: Pageable): List<Item>

    /**
     * Получение владельцем списка всех его предметов.
     * @param requestId идентификатор пользователя
     */
    fun findAllByRequestId(requestId: Long): List<Item>

    fun findAllByRequestIdNotNull(): List<Item>

    companion object {
        private const val SEARCH = """
            SELECT item
            FROM Item AS item
            WHERE item.available = true
            AND (item.name iLIKE %:text% OR item.description iLIKE %:text%)
            """
    }
}
