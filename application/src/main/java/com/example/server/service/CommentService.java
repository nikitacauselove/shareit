package com.example.server.service;

import com.example.api.dto.CommentDto;
import com.example.server.repository.entity.Comment;

/**
 * Сервис для взаимодействия с отзывами.
 */
public interface CommentService {

    /**
     * Добавление нового отзыва.
     * @param itemId идентификатор предмета
     * @param commentDto информация об отзыве
     * @param userId идентификатор пользователя
     */
    Comment create(Long itemId, CommentDto commentDto, Long userId);
}
