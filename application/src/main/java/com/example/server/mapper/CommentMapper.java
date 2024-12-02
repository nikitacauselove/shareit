package com.example.server.mapper;

import com.example.api.dto.CommentDto;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "text", source = "commentDto.text")
    @Mapping(target = "created", ignore = true)
    Comment toComment(CommentDto commentDto, Item item, User author);

    @Mapping(target = "authorName", source = "comment.author.name")
    CommentDto toCommentDto(Comment comment);

    List<CommentDto> toCommentDto(List<Comment> comments);
}
