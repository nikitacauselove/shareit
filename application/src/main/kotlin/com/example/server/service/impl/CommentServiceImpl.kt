package com.example.server.service.impl

import com.example.api.dto.CommentDto
import com.example.server.mapper.CommentMapper
import com.example.server.repository.BookingRepository
import com.example.server.repository.CommentRepository
import com.example.server.repository.ItemRepository
import com.example.server.repository.UserRepository
import com.example.server.repository.entity.Comment
import com.example.server.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime

@Service
class CommentServiceImpl(
    private val bookingRepository: BookingRepository,
    private val commentMapper: CommentMapper,
    private val commentRepository: CommentRepository,
    private val itemRepository: ItemRepository,
    private val userRepository: UserRepository
) : CommentService {

    @Transactional
    override fun create(itemId: Long, commentDto: CommentDto, userId: Long): Comment {
        val item = itemRepository.findById(itemId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Предмет с указанным идентификатором не найден") }
        val author = userRepository.findById(userId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден") }

        if (!bookingRepository.existsByBookerIdAndItemIdAndEndBefore(userId, itemId, LocalDateTime.now())) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователи могут оставлять отзывы на предмет только после того, как взяли его в аренду")
        }
        return commentRepository.save(commentMapper.toComment(commentDto, item, author))
    }
}
