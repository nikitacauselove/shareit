package com.example.server.mapper

import com.example.api.model.CommentDto
import com.example.server.entity.Comment
import com.example.server.entity.Item
import com.example.server.entity.User

fun CommentDto.toEntity(item: Item, author: User): Comment {
    return Comment(id = this.id, text = this.text, item = item, author = author, created = null)
}

fun Comment.toDto(): CommentDto {
    return CommentDto(id = this.id, text = this.text, authorName = this.author.name, created = this.created)
}

fun List<Comment>.toDto(): List<CommentDto> {
    return map { it.toDto() }
}
