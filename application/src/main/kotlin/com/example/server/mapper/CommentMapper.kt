package com.example.server.mapper

import com.example.api.dto.CommentDto
import com.example.server.repository.entity.Comment
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class CommentMapper {

    fun toComment(commentDto: CommentDto, item: Item, author: User): Comment {
        val comment = Comment()

        comment.text = commentDto.text
        comment.item = item
        comment.author = author

        return comment
    }

    fun toCommentDto(comment: Comment): CommentDto {
        var authorName: String? = null
        var id: Long? = null
        var text: String? = null
        var created: LocalDateTime? = null

        authorName = commentAuthorName(comment)
        id = comment.id
        text = comment.text
        created = comment.created

        val commentDto = CommentDto(id, text, authorName, created)

        return commentDto
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
