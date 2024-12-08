package com.example.api;

import com.example.api.dto.CommentDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.example.api.UserApi.X_SHARER_USER_ID;

public interface CommentApi {

    String PATH = "v1/items";

    @PostMapping("/{itemId}/comment")
    CommentDto create(@PathVariable Long itemId, @RequestBody @Valid CommentDto commentCreationDto, @RequestHeader(X_SHARER_USER_ID) Long authorId);
}
