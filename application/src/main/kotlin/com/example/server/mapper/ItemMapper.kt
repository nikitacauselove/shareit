package com.example.server.mapper

import com.example.api.dto.ItemDto
import com.example.api.dto.ItemDtoWithBooking
import com.example.server.mapper.decorator.ItemMapperDecorator
import com.example.server.repository.entity.Booking
import com.example.server.repository.entity.Comment
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.ItemRequest
import com.example.server.repository.entity.User
import org.mapstruct.Context
import org.mapstruct.DecoratedWith
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy

@DecoratedWith(ItemMapperDecorator::class)
@Mapper(componentModel = "spring", uses = [CommentMapper::class])
interface ItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "itemDto.name")
    @Mapping(target = "description", source = "itemDto.description")
    @Mapping(target = "request", source = "itemRequest")
    fun toItem(itemDto: ItemDto, owner: User, itemRequest: ItemRequest?): Item

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "itemDto.name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    @Mapping(target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "available", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "request", ignore = true)
    fun updateItem(itemDto: ItemDto, @MappingTarget item: Item): Item

    @Mapping(target = "requestId", source = "item.request.id", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    fun toItemDto(item: Item): ItemDto

    fun toItemDto(itemList: List<Item>): List<ItemDto>

    @Mapping(target = "lastBooking", ignore = true)
    @Mapping(target = "nextBooking", ignore = true)
    @Mapping(target = "comments", ignore = true)
    fun toItemDtoWithBooking(item: Item, @Context bookingList: List<Booking>, @Context commentList: List<Comment>): ItemDtoWithBooking

    fun toItemDtoWithBooking(items: List<Item>, @Context bookingList: List<Booking>, @Context commentList: List<Comment>): List<ItemDtoWithBooking>
}
