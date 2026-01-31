package com.example.server.mapper

import com.example.api.model.ItemDto
import com.example.api.model.ItemDtoWithBooking
import com.example.server.entity.Booking
import com.example.server.entity.Comment
import com.example.server.entity.Item
import com.example.server.entity.ItemRequest
import com.example.server.entity.User
import com.example.server.mapper.decorator.ItemMapperDecorator
import org.mapstruct.Context
import org.mapstruct.DecoratedWith
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.MappingTarget
import org.mapstruct.NullValuePropertyMappingStrategy

@DecoratedWith(ItemMapperDecorator::class)
@Mapper(componentModel = "spring", uses = [CommentMapper::class])
interface ItemMapper {

    @Mapping(target = "id", source = "itemDto.id")
    @Mapping(target = "name", source = "itemDto.name")
    @Mapping(target = "description", source = "itemDto.description")
    @Mapping(target = "request", source = "itemRequest")
    @Mapping(target = "bookings", source = "bookingList")
    @Mapping(target = "comments", source = "commentList")
    fun toEntity(itemDto: ItemDto, owner: User, itemRequest: ItemRequest?, bookingList: MutableList<Booking>, commentList: MutableList<Comment>): Item

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "description", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "available", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "request", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "comments", ignore = true)
    fun updateEntity(itemDto: ItemDto, @MappingTarget item: Item): Item

    @Mapping(target = "requestId", source = "item.request.id")
    fun toDto(item: Item): ItemDto

    fun toDto(itemList: List<Item>): List<ItemDto>

    @Mapping(target = "lastBooking", ignore = true)
    @Mapping(target = "nextBooking", ignore = true)
    @Mapping(target = "comments", ignore = true)
    fun toDtoWithBooking(item: Item, @Context bookingList: List<Booking>): ItemDtoWithBooking

    fun toDtoWithBooking(items: List<Item>, @Context bookingList: List<Booking>): List<ItemDtoWithBooking>
}

fun ItemDto.toEntity(owner: User, itemRequest: ItemRequest?, bookingList: MutableList<Booking>, commentList: MutableList<Comment>): Item {
    return Item(id = this.id, name = this.name!!, description = this.description!!, available = this.available ?: false, owner = owner, request = itemRequest, bookings = bookingList, comments = commentList)
}

fun Item.updateEntity(itemDto: ItemDto): Item {
    this.name = itemDto.name ?: this.name
    this.description = itemDto.description ?: this.description
    this.available = itemDto.available ?: this.available
    return this
}

fun Item.toDto(): ItemDto {
    return ItemDto(id = this.id, name = this.name, description = this.description, available = this.available, requestId = this.request?.id)
}

fun List<Item>.toDto(): List<ItemDto> {
    return map { it.toDto() }
}