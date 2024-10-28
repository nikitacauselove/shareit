package com.example.server.controller;

import com.example.server.service.CommentService;
import com.example.server.dto.CommentDto;
import com.example.server.mapper.CommentMapper;
import com.example.server.repository.entity.Comment;
import com.example.server.service.ItemService;
import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static com.example.server.controller.UserController.X_SHARER_USER_ID;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    private final ItemService itemService;
    private final UserService userService;

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<CommentDto> create(@PathVariable long itemId, @RequestBody CommentDto commentCreationDto, @RequestHeader(X_SHARER_USER_ID) long authorId) {
        Comment comment = CommentMapper.toComment(commentCreationDto, itemService.findById(itemId), userService.findById(authorId));

        log.info("Добавление нового отзыва на вещь с идентификатором {} пользователем с идентификатором {}.", itemId, authorId);
        return ResponseEntity.ok(CommentMapper.toCommentDto(commentService.create(comment)));
    }
}
