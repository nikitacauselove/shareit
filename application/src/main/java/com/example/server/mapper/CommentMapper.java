package com.example.server.mapper;

import com.example.api.dto.CommentDto;
import com.example.server.repository.entity.Comment;
import com.example.server.repository.entity.Item;
import com.example.server.repository.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    public Comment toComment(CommentDto commentDto, Item item, User author) {
        if ( commentDto == null && item == null && author == null ) {
            return null;
        }

        Comment comment = new Comment();

        if ( commentDto != null ) {
            comment.setText( commentDto.getText() );
        }
        comment.setItem( item );
        comment.setAuthor( author );

        return comment;
    }

    public CommentDto toCommentDto(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        String authorName = null;
        Long id = null;
        String text = null;
        LocalDateTime created = null;

        authorName = commentAuthorName( comment );
        id = comment.getId();
        text = comment.getText();
        created = comment.getCreated();

        CommentDto commentDto = new CommentDto( id, text, authorName, created );

        return commentDto;
    }

    public List<CommentDto> toCommentDto(List<Comment> comments) {
        if ( comments == null ) {
            return null;
        }

        List<CommentDto> list = new ArrayList<CommentDto>( comments.size() );
        for ( Comment comment : comments ) {
            list.add( toCommentDto( comment ) );
        }

        return list;
    }

    private String commentAuthorName(Comment comment) {
        User author = comment.getAuthor();
        if ( author == null ) {
            return null;
        }
        return author.getName();
    }
}
