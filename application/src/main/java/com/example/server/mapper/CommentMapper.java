package com.example.server.mapper;

import com.example.api.dto.CommentDto;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    public Comment toComment(CommentDto commentDto, Item item, User author) {
        LocalDateTime now = LocalDateTime.now();

        return new Comment(null, commentDto.text(), item, author, now);
    }

    public CommentDto toCommentDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getAuthor().getName(), comment.getCreated());
    }

    public List<CommentDto> toCommentDto(List<Comment> comments) {
        return comments.stream()
                .map(this::toCommentDto)
                .collect(Collectors.toList());
    }
}
