package com.example.server.mapper

import com.example.api.model.ItemDto
import com.example.api.model.ItemDtoWithBooking
import com.example.server.entity.Booking
import com.example.server.entity.Comment
import com.example.server.entity.Item
import com.example.server.entity.ItemRequest
import com.example.server.entity.User

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

fun Item.toDtoWithBooking(bookingList: List<Booking>): ItemDtoWithBooking {
    return ItemDtoWithBooking(id = this.id!!, name = this.name, description = this.description, available = this.available, lastBooking = bookingList.findLast(), nextBooking = bookingList.findNext(), comments = this.comments.toDto())
}

fun List<Item>.toDtoWithBooking(bookingList: List<Booking>): List<ItemDtoWithBooking> {
    return map { item ->
        val bookings = bookingList.filter { it.item.id == item.id }

        item.toDtoWithBooking(bookings)
    }
}