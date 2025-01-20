package com.example.server.mapper

import com.example.api.dto.ItemRequestDto
import com.example.server.mapper.decorator.ItemRequestMapperDecorator
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.ItemRequest
import com.example.server.repository.entity.User
import org.mapstruct.Context
import org.mapstruct.DecoratedWith
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@DecoratedWith(ItemRequestMapperDecorator::class)
@Mapper(componentModel = "spring", uses = [ItemMapper::class])
interface ItemRequestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    fun toItemRequest(itemRequestDto: ItemRequestDto, requester: User): ItemRequest

    @Mapping(target = "requesterId", ignore = true)
    @Mapping(target = "items", ignore = true)
    fun toItemRequestDto(itemRequest: ItemRequest, @Context itemList: List<Item>): ItemRequestDto

    fun toItemRequestDto(itemRequestList: List<ItemRequest>, @Context itemList: List<Item>): List<ItemRequestDto>
}
