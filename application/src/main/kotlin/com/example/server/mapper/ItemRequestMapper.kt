package com.example.server.mapper

import com.example.api.model.ItemRequestDto
import com.example.server.entity.Item
import com.example.server.entity.ItemRequest
import com.example.server.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@Mapper(componentModel = "spring", uses = [ItemMapper::class])
interface ItemRequestMapper {

    @Mapping(target = "id", source = "itemRequestDto.id")
    @Mapping(target = "items", source = "itemList")
    fun toEntity(itemRequestDto: ItemRequestDto, requester: User, itemList: MutableList<Item>): ItemRequest

    @Mapping(target = "requesterId", source = "itemRequest.id")
    fun toDto(itemRequest: ItemRequest): ItemRequestDto

    fun toDto(itemRequestList: List<ItemRequest>): List<ItemRequestDto>
}
