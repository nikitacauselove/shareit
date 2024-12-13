package com.example.server.controller;

import com.example.api.CommentApi;
import com.example.api.dto.CommentDto;
import com.example.server.service.CommentService;
import com.example.server.service.ItemService;
import com.example.server.service.UserService;
import com.example.server.mapper.CommentMapper;
import com.example.server.repository.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = CommentApi.PATH)
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;
    private final CommentMapper commentMapper;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public CommentDto create(Long itemId, CommentDto commentDto, Long authorId) {
        Comment comment = commentMapper.toComment(commentDto, itemService.findById(itemId), userService.findById(authorId));

        return commentMapper.toCommentDto(commentService.create(comment));
    }
}
