package com.example.server.service;

import com.example.api.dto.ItemDto;
import com.example.api.dto.ItemDtoWithBookings;
import com.example.server.repository.entity.Item;

import java.util.List;

/**
 * Сервис для взаимодействия с предметами.
 */
public interface ItemService {

    /**
     * Добавление нового предмета.
     * @param itemDto информация о предмете
     * @param userId идентификатор пользователя
     */
    Item create(ItemDto itemDto, Long userId);

    /**
     * Обновление информации о предмете.
     * @param id идентификатор предмета
     * @param itemDto информация о предмете
     * @param userId идентификатор пользователя
     */
    Item update(Long id, ItemDto itemDto, Long userId);

    /**
     * Получение информации о предмете.
     * @param id идентификатор предмета
     */
    Item findById(Long id);

    ItemDtoWithBookings findByIdWithBooking(Long id, Long userId);

    /**
     * Получение владельцем списка всех его предметов.
     * @param userId идентификатор пользователя
     */
    List<ItemDtoWithBookings> findAllByOwnerId(Long userId, Integer from, Integer size);

    /**
     * Поиск предметов.
     * @param text текст для поиска
     */
    List<Item> search(String text, Integer from, Integer size);
}
