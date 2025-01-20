package com.example.server.service

import com.example.api.dto.ItemDto
import com.example.api.dto.ItemDtoWithBooking
import com.example.server.repository.entity.Item

/**
 * Сервис для взаимодействия с предметами.
 */
interface ItemService {

    /**
     * Добавление нового предмета.
     * @param itemDto информация о предмете
     * @param userId идентификатор пользователя
     */
    fun create(itemDto: ItemDto, userId: Long): Item

    /**
     * Обновление информации о предмете.
     * @param id идентификатор предмета
     * @param itemDto информация о предмете
     * @param userId идентификатор пользователя
     */
    fun update(id: Long, itemDto: ItemDto, userId: Long): Item

    /**
     * Получение информации о предмете.
     * @param id идентификатор предмета
     */
    fun findById(id: Long): Item

    fun findByIdWithBooking(id: Long, userId: Long): ItemDtoWithBooking

    /**
     * Получение владельцем списка всех его предметов.
     * @param userId идентификатор пользователя
     */
    fun findAllByOwnerId(userId: Long, from: Int, size: Int): List<ItemDtoWithBooking>

    /**
     * Поиск предметов.
     * @param text текст для поиска
     */
    fun search(text: String, from: Int, size: Int): List<Item>
}
