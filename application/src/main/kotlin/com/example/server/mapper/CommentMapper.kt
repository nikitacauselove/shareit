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

fun CommentDto.toEntity(item: Item, author: User): Comment = Comment(
    id = this.id, text = this.text, item = item, author = author, created = null
)

fun Comment.toDto(): CommentDto = CommentDto(
    id = this.id, text = this.text, authorName = this.author.name, created = this.created
)

fun List<Comment>.commentDtoList(): List<CommentDto> = this.map { it.toDto() }
