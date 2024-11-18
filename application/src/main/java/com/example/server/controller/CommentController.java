package com.example.server.controller;

import com.example.api.CommentApi;
import com.example.api.dto.CommentDto;
import com.example.server.service.CommentService;
import com.example.server.mapper.CommentMapper;
import com.example.server.repository.entity.Comment;
import com.example.server.service.ItemService;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = CommentApi.PATH)
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentService commentService;
    private final ItemService itemService;
    private final UserService userService;

    @Override
    public CommentDto create(Long itemId, CommentDto commentCreationDto, Long authorId) {
        Comment comment = CommentMapper.toComment(commentCreationDto, itemService.findById(itemId), userService.findById(authorId));

        log.info("Добавление нового отзыва на вещь с идентификатором {} пользователем с идентификатором {}.", itemId, authorId);
        return CommentMapper.toCommentDto(commentService.create(comment));
    }
}
