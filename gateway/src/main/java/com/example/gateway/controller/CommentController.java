package com.example.gateway.controller;

import com.example.api.CommentApi;
import com.example.api.dto.CommentDto;
import com.example.gateway.client.CommentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = CommentApi.PATH)
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentClient commentClient;

    @Override
    public CommentDto create(Long itemId, CommentDto commentDto, Long userId) {
        return commentClient.create(itemId, commentDto, userId);
    }
}
