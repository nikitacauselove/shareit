package com.example.gateway.controller

import com.example.api.CommentApi
import com.example.api.model.CommentDto
import com.example.gateway.client.CommentClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [CommentApi.PATH])
class CommentController(
    val commentClient: CommentClient
) : CommentApi {

    override fun create(itemId: Long, commentDto: CommentDto, userId: Long): CommentDto {
        return commentClient.create(itemId, commentDto, userId)
    }
}
