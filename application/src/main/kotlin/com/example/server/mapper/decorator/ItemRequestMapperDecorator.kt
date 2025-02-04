package com.example.server.mapper.decorator

import com.example.api.dto.ItemRequestDto
import com.example.server.mapper.ItemMapper
import com.example.server.mapper.ItemRequestMapper
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.ItemRequest
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Collectors

abstract class ItemRequestMapperDecorator : ItemRequestMapper {

    private var delegate: ItemRequestMapper? = null
    private var itemMapper: ItemMapper? = null

    override fun toItemRequestDto(itemRequest: ItemRequest, itemList: List<Item>): ItemRequestDto {
        return ItemRequestDto(
            id = itemRequest.id!!,
            description = itemRequest.description,
            requesterId = itemRequest.requester.id,
            created = itemRequest.created!!,
            items = itemMapper!!.toItemDto(itemList)
        )
    }

    override fun toItemRequestDto(itemRequestList: List<ItemRequest>, itemList: List<Item>): List<ItemRequestDto> {
        val requestIdToItemList = itemList.stream()
            .collect(Collectors.groupingBy { item: Item -> item.request!!.id })

        return itemRequestList.stream()
            .map { itemRequest: ItemRequest -> toItemRequestDto(itemRequest, requestIdToItemList.getOrDefault(itemRequest.id, emptyList())) }
            .collect(Collectors.toList())
    }

    @Autowired
    fun setDelegate(delegate: ItemRequestMapper) {
        this.delegate = delegate
    }

    @Autowired
    fun setItemMapper(itemMapper: ItemMapper) {
        this.itemMapper = itemMapper
    }
}
