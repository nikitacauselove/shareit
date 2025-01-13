package com.example.gateway.controller

import com.example.api.ItemRequestApi
import com.example.api.dto.ItemRequestDto
import com.example.gateway.client.ItemRequestClient
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = [ItemRequestApi.PATH])
class ItemRequestController(
    val requestClient: ItemRequestClient
) : ItemRequestApi {

    override fun create(itemRequestDto: ItemRequestDto?, userId: Long?): ItemRequestDto? {
        return requestClient.create(itemRequestDto, userId)
    }

    override fun findById(id: Long?, userId: Long?): ItemRequestDto? {
        return requestClient.findById(id, userId)
    }

    override fun findAllByRequesterId(userId: Long?): List<ItemRequestDto?>? {
        return requestClient.findAllByRequesterId(userId)
    }

    override fun findAllByRequesterIdNot(userId: Long?, from: Int?, size: Int?): List<ItemRequestDto?>? {
        return requestClient.findAllByRequesterIdNot(userId, from, size)
    }
}
