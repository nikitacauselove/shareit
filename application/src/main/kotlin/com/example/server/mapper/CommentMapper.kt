package com.example.server.mapper

import com.example.api.dto.CommentDto
import com.example.server.repository.entity.Comment
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "created", ignore = true)
    fun toComment(commentDto: CommentDto, item: Item, author: User): Comment

    @Mapping(target = "authorName", source = "comment.author.name")
    fun toCommentDto(comment: Comment): CommentDto

    fun toCommentDto(commentList: List<Comment>): List<CommentDto>
}
