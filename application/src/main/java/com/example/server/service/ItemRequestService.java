package com.example.server.service;

import com.example.api.dto.ItemRequestDto;
import com.example.server.repository.entity.ItemRequest;

import java.util.List;

/**
 * Сервис для взаимодействия с запросами на добавление предмета.
 */
public interface ItemRequestService {

    /**
     * Добавление запроса на добавление предмета.
     * @param itemRequestDto информация о запросе на добавление предмета
     * @param userId идентификатор пользователя
     */
    ItemRequestDto create(ItemRequestDto itemRequestDto, Long userId);

    /**
     * Получение информации о запросе на добавление предмета.
     * @param id идентификатор предмета
     */
    ItemRequest findById(Long id);

    ItemRequestDto findByIdWithItems(Long id, Long userId);

    /**
     * Получение списка всех запросов, добавленных пользователем.
     * @param userId идентификатор пользователя
     */
    List<ItemRequestDto> findAllByRequesterId(Long userId);

    /**
     * Получение списка всех запросов, добавленных другими пользователями.
     * @param userId идентификатор пользователя
     */
    List<ItemRequestDto> findAllByRequesterIdNot(Long userId, Integer from, Integer size);
}
