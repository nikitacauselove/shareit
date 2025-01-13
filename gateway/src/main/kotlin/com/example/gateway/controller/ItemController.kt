package com.example.gateway.controller

import com.example.api.ItemApi
import com.example.api.dto.ItemDto
import com.example.api.dto.ItemDtoWithBookings
import com.example.gateway.client.ItemClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [ItemApi.PATH])
class ItemController(
    val itemClient: ItemClient
) : ItemApi {

    override fun create(itemDto: ItemDto?, userId: Long?): ItemDto? {
        return itemClient.create(itemDto, userId)
    }

    override fun update(id: Long?, itemDto: ItemDto?, userId: Long?): ItemDto? {
        return itemClient.update(id, itemDto, userId)
    }

    override fun findById(id: Long?, userId: Long?): ItemDtoWithBookings? {
        return itemClient.findById(id, userId)
    }

    override fun findAllByOwnerId(userId: Long?, from: Int?, size: Int?): List<ItemDtoWithBookings?>? {
        return itemClient.findAllByOwnerId(userId, from, size)
    }

    override fun search(text: String?, from: Int?, size: Int?): List<ItemDto?>? {
        return itemClient.search(text, from, size)
    }
}
