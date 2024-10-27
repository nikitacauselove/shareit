package com.example.api;

import com.example.api.dto.CommentDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.example.api.Constant.X_SHARER_USER_ID;

public interface CommentApi {

    String PATH = "/items";

    @PostMapping("/{itemId}/comment")
    ResponseEntity<Object> create(@PathVariable long itemId, @RequestBody @Valid CommentDto commentCreationDto, @RequestHeader(X_SHARER_USER_ID) long authorId);
}
