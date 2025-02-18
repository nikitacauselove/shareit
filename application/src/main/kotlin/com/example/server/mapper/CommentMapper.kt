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
    @Mapping(target = "created", ignore = true)
    fun toEntity(commentDto: CommentDto, item: Item, author: User): Comment

    @Mapping(target = "authorName", source = "comment.author.name")
    fun toDto(comment: Comment): CommentDto

    fun toDto(commentList: List<Comment>): List<CommentDto>
}
