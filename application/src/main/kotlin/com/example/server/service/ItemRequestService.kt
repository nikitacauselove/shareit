package com.example.server.service

import com.example.api.model.ItemRequestDto
import com.example.server.repository.entity.ItemRequest

/**
 * Сервис для взаимодействия с запросами на добавление предмета.
 */
interface ItemRequestService {

    /**
     * Добавление запроса на добавление предмета.
     *
     * @param itemRequestDto информация о запросе на добавление предмета
     * @param userId идентификатор пользователя
     */
    fun create(itemRequestDto: ItemRequestDto, userId: Long): ItemRequest

    /**
     * Получение информации о запросе на добавление предмета.
     *
     * @param id идентификатор предмета
     */
    fun findById(id: Long): ItemRequest

    /**
     * Получение информации о запросе на добавление предмета.
     *
     * @param id идентификатор предмета
     * @param userId идентификатор пользователя
     */
    fun findByIdWithItems(id: Long, userId: Long): ItemRequestDto

    /**
     * Получение списка всех запросов, добавленных пользователем.
     *
     * @param userId идентификатор пользователя
     */
    fun findAllByRequesterId(userId: Long): List<ItemRequestDto>

    /**
     * Получение списка всех запросов, добавленных другими пользователями.
     *
     * @param userId идентификатор пользователя
     * @param from индекс первого элемента
     * @param size количество элементов для отображения
     */
    fun findAllByRequesterIdNot(userId: Long, from: Int, size: Int): List<ItemRequestDto>
}
