package com.example.api;

import com.example.api.dto.CommentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import static com.example.api.UserApi.X_SHARER_USER_ID;

@Tag(name = "Отзывы", description = "Взаимодействие с отзывами")
public interface CommentApi {

    String PATH = "v1/items";

    @PostMapping("/{itemId}/comment")
    @Operation(description = "Добавление нового отзыва")
    CommentDto create(@PathVariable Long itemId, @RequestBody @Valid CommentDto commentDto, @RequestHeader(X_SHARER_USER_ID) Long authorId);
}
