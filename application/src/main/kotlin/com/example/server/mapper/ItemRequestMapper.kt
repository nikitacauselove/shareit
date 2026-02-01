package com.example.server.mapper

import com.example.api.model.ItemRequestDto
import com.example.server.entity.Item
import com.example.server.entity.ItemRequest
import com.example.server.entity.User

fun ItemRequestDto.toEntity(requester: User, itemList: MutableList<Item>): ItemRequest {
    return ItemRequest(id = this.id, description = this.description!!, requester = requester, created = this.created, items = itemList)
}

fun ItemRequest.toDto(): ItemRequestDto {
    return ItemRequestDto(id = this.id, description = this.description, requesterId = this.requester.id, created = this.created, items = this.items.toDto())
}

fun List<ItemRequest>.toDto(): List<ItemRequestDto> {
    return map { it.toDto() }
}