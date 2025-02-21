package com.example.api

import com.example.api.model.CommentDto
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader

@Tag(name = "Отзывы", description = "Взаимодействие с отзывами")
interface CommentApi {

    @PostMapping("/{itemId}/comment")
    @Operation(description = "Добавление нового отзыва")
    fun create(@PathVariable itemId: Long, @RequestBody @Valid commentDto: CommentDto, @RequestHeader(UserApi.X_SHARER_USER_ID) userId: Long): CommentDto

    companion object {
        const val PATH = "v1/items"
    }
}
