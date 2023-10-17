package com.example.server.comment.dto;

import com.example.server.comment.model.Comment;
import com.example.server.item.model.Item;
import com.example.server.user.model.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommentMapper {
    public Comment toComment(CommentDto commentDto, Item item, User author) {
        LocalDateTime now = LocalDateTime.now();

        return new Comment(null, commentDto.getText(), item, author, now);
    }

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getAuthor().getName(), comment.getCreated());
    }

    public List<CommentDto> toCommentDto(List<Comment> comments) {
        return comments.stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }
}
