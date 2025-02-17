package com.example.server.mapper.decorator

import com.example.api.model.ItemRequestDto
import com.example.server.mapper.ItemMapper
import com.example.server.mapper.ItemRequestMapper
import com.example.server.entity.Item
import com.example.server.entity.ItemRequest
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Collectors

abstract class ItemRequestMapperDecorator : ItemRequestMapper {

    @Autowired
    private lateinit var delegate: ItemRequestMapper

    @Autowired
    private lateinit var itemMapper: ItemMapper

    override fun toItemRequestDto(itemRequest: ItemRequest, itemList: List<Item>): ItemRequestDto {
        return ItemRequestDto(
            id = itemRequest.id,
            description = itemRequest.description,
            requesterId = itemRequest.requester.id,
            created = itemRequest.created,
            items = itemMapper.toItemDto(itemList)
        )
    }

    override fun toItemRequestDto(itemRequestList: List<ItemRequest>, itemList: List<Item>): List<ItemRequestDto> {
        val requestIdToItemList = itemList.stream()
            .collect(Collectors.groupingBy { item -> item.request!!.id })

        return itemRequestList.stream()
            .map { itemRequest -> toItemRequestDto(itemRequest, requestIdToItemList.getOrDefault(itemRequest.id, emptyList())) }
            .toList()
    }
}
