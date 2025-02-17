package com.example.server.service

import com.example.api.model.CommentDto
import com.example.server.entity.Comment

/**
 * Сервис для взаимодействия с отзывами.
 */
interface CommentService {

    /**
     * Добавление нового отзыва.
     *
     * @param itemId идентификатор предмета
     * @param commentDto информация об отзыве
     * @param userId идентификатор пользователя
     */
    fun create(itemId: Long, commentDto: CommentDto, userId: Long): Comment
}
