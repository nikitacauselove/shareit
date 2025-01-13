package com.example.server.controller;

import com.example.api.CommentApi;
import com.example.api.dto.CommentDto;
import com.example.server.service.CommentService;
import com.example.server.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = CommentApi.PATH)
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentMapper commentMapper;
    private final CommentService commentService;

    @Override
    public CommentDto create(Long itemId, CommentDto commentDto, Long userId) {
        return commentMapper.toCommentDto(commentService.create(itemId, commentDto, userId));
    }
}
