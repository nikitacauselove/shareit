package com.example.server.mapper

import com.example.api.model.ItemRequestDto
import com.example.server.mapper.decorator.ItemRequestMapperDecorator
import com.example.server.entity.Item
import com.example.server.entity.ItemRequest
import com.example.server.entity.User
import org.mapstruct.Context
import org.mapstruct.DecoratedWith
import org.mapstruct.Mapper
import org.mapstruct.Mapping

@DecoratedWith(ItemRequestMapperDecorator::class)
@Mapper(componentModel = "spring", uses = [ItemMapper::class])
interface ItemRequestMapper {

    @Mapping(target = "id", source = "itemRequestDto.id")
    fun toEntity(itemRequestDto: ItemRequestDto, requester: User): ItemRequest

    @Mapping(target = "requesterId", source = "itemRequest.id")
    @Mapping(target = "items", ignore = true)
    fun toDto(itemRequest: ItemRequest, @Context itemList: List<Item>): ItemRequestDto

    fun toDto(itemRequestList: List<ItemRequest>, @Context itemList: List<Item>): List<ItemRequestDto>
}
