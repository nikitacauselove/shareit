package com.example.server.repository

import com.example.server.repository.entity.ItemRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Репозиторий для взаимодействия с запросами на добавление предмета.
 */
@Repository
interface ItemRequestRepository : JpaRepository<ItemRequest, Long> {

    /**
     * Получение списка всех запросов, добавленных пользователем.
     *
     * @param requesterId идентификатор пользователя
     */
    fun findAllByRequesterId(requesterId: Long, sort: Sort): List<ItemRequest>

    /**
     * Получение списка всех запросов, добавленных другими пользователем.
     *
     * @param requesterId идентификатор пользователя
     */
    fun findAllByRequesterIdNot(requesterId: Long, pageable: Pageable): List<ItemRequest>

    companion object {
        const val NOT_FOUND = "Запрос на добавление предмета с указанным идентификатором не найден"
    }
}
