package com.example.server.controller

import com.example.api.ItemRequestApi
import com.example.api.dto.ItemRequestDto
import com.example.server.mapper.ItemRequestMapper
import com.example.server.service.ItemRequestService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [ItemRequestApi.PATH])
class ItemRequestController(
    private val itemRequestMapper: ItemRequestMapper,
    private val itemRequestService: ItemRequestService
) : ItemRequestApi {

    override fun create(itemRequestDto: ItemRequestDto, userId: Long): ItemRequestDto {
        return itemRequestMapper.toItemRequestDto(itemRequestService.create(itemRequestDto, userId), emptyList())
    }

    override fun findById(id: Long, userId: Long): ItemRequestDto {
        return itemRequestService.findByIdWithItems(id, userId)
    }

    override fun findAllByRequesterId(userId: Long): List<ItemRequestDto> {
        return itemRequestService.findAllByRequesterId(userId)
    }

    override fun findAllByRequesterIdNot(userId: Long, from: Int, size: Int): List<ItemRequestDto> {
        return itemRequestService.findAllByRequesterIdNot(userId, from, size)
    }
}
