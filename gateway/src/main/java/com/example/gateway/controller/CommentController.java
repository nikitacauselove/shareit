package com.example.gateway.controller;

import com.example.gateway.client.CommentClient;
import com.example.gateway.dto.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.gateway.controller.UserController.X_SHARER_USER_ID;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Validated
public class CommentController {
    private final CommentClient commentClient;

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> create(@PathVariable long itemId, @RequestBody @Valid CommentDto commentCreationDto, @RequestHeader(X_SHARER_USER_ID) long authorId) {
        return commentClient.create(itemId, commentCreationDto, authorId);
    }
}
