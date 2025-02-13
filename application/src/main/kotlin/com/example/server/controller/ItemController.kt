package com.example.server.controller

import com.example.api.ItemApi
import com.example.api.model.ItemDto
import com.example.api.model.ItemDtoWithBooking
import com.example.server.mapper.ItemMapper
import com.example.server.service.ItemService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [ItemApi.PATH])
class ItemController(
    private val itemMapper: ItemMapper,
    private val itemService: ItemService
) : ItemApi {

    override fun create(itemDto: ItemDto, userId: Long): ItemDto {
        return itemMapper.toItemDto(itemService.create(itemDto, userId))
    }

    override fun update(id: Long, itemDto: ItemDto, userId: Long): ItemDto {
        return itemMapper.toItemDto(itemService.update(id, itemDto, userId))
    }

    override fun findById(id: Long, userId: Long): ItemDtoWithBooking {
        return itemService.findByIdWithBooking(id, userId)
    }

    override fun findAllByOwnerId(userId: Long, from: Int, size: Int): List<ItemDtoWithBooking> {
        return itemService.findAllByOwnerId(userId, from, size)
    }

    override fun search(text: String, from: Int, size: Int): List<ItemDto> {
        return itemMapper.toItemDto(itemService.search(text, from, size))
    }
}
