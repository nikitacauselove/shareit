package com.example.server.repository

import com.example.server.entity.Item
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * Репозиторий для взаимодействия с предметами.
 */
interface ItemRepository : JpaRepository<Item, Long> {

    /**
     * Получение владельцем списка всех его предметов.
     *
     * @param ownerId идентификатор пользователя
     */
    fun findAllByOwnerId(ownerId: Long, pageable: Pageable): List<Item>

    /**
     * Поиск предметов.
     *
     * @param text текст для поиска
     */
    @Query(SEARCH)
    fun search(text: String, pageable: Pageable): List<Item>

    companion object {
        const val NOT_FOUND = "Предмет с указанным идентификатором не найден"

        private const val SEARCH = """
            SELECT item
            FROM Item AS item
            WHERE item.available = true
            AND (item.name iLIKE %:text% OR item.description iLIKE %:text%)
            """
    }
}
