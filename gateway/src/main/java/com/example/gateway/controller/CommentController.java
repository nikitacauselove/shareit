package com.example.gateway.controller;

import com.example.api.CommentApi;
import com.example.api.dto.CommentDto;
import com.example.gateway.client.CommentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = CommentApi.PATH)
@RequiredArgsConstructor
public class CommentController implements CommentApi {

    private final CommentClient commentClient;

    @Override
    public ResponseEntity<Object> create(long itemId, CommentDto commentCreationDto, long authorId) {
        return commentClient.create(itemId, commentCreationDto, authorId);
    }
}
