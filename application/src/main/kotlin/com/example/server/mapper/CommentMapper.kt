package com.example.server.mapper

import com.example.api.dto.CommentDto
import com.example.server.repository.entity.Comment
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.User
import org.springframework.stereotype.Component

@Component
class CommentMapper {

    fun toComment(commentDto: CommentDto, item: Item, author: User): Comment {
        return Comment(
            id = null,
            text = commentDto.text,
            item = item,
            author = author,
            created = null
        )
    }

    fun toCommentDto(comment: Comment): CommentDto {
        val authorName = commentAuthorName(comment)
        val id = comment.id
        val text = comment.text
        val created = comment.created

        return CommentDto(id, text, authorName, created)
    }

    fun toCommentDto(comments: List<Comment>): List<CommentDto> {
        val list: MutableList<CommentDto> = ArrayList(comments.size)

        for (comment in comments) {
            list.add(toCommentDto(comment))
        }
        return list
    }

    private fun commentAuthorName(comment: Comment): String? {
        val author = comment.author ?: return null

        return author.name
    }
}
