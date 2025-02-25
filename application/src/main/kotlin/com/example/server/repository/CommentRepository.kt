package com.example.server.repository

import com.example.server.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository

/**
 * Репозиторий для взаимодействия с отзывами.
 */
interface CommentRepository : JpaRepository<Comment, Long>
