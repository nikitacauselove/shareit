package com.example.server.controller

import com.example.api.CommentApi
import com.example.api.model.CommentDto
import com.example.server.mapper.CommentMapper
import com.example.server.service.CommentService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [CommentApi.PATH])
class CommentController(
    private val commentMapper: CommentMapper,
    private val commentService: CommentService
) : CommentApi {

    override fun create(itemId: Long, commentDto: CommentDto, userId: Long): CommentDto {
        return commentMapper.toDto(commentService.create(itemId, commentDto, userId))
    }
}
