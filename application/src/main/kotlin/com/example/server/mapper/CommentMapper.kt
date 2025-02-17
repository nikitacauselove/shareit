package com.example.server.mapper

import com.example.api.model.CommentDto
import com.example.server.entity.Comment
import com.example.server.entity.Item
import com.example.server.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring")
interface CommentMapper {

    @Mapping(target = "id", source = "commentDto.id")
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "created", ignore = true)
    fun toComment(commentDto: CommentDto, item: Item, author: User): Comment

    @Mapping(target = "authorName", source = "comment.author.name")
    fun toCommentDto(comment: Comment): CommentDto

    fun toCommentDto(commentList: List<Comment>): List<CommentDto>
}
