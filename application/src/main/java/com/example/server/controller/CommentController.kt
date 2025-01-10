package com.example.server.controller

import com.example.api.CommentApi
import com.example.api.dto.CommentDto
import com.example.server.mapper.CommentMapper
import com.example.server.service.CommentService
import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [CommentApi.PATH])
@RequiredArgsConstructor
class CommentController(
    private val commentMapper: CommentMapper,
    private val commentService: CommentService
) : CommentApi {

    override fun create(itemId: Long, commentDto: CommentDto, userId: Long): CommentDto {
        return commentMapper.toCommentDto(commentService.create(itemId, commentDto, userId))
    }
}
