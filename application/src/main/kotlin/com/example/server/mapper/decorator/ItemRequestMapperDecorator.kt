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

    override fun toDto(itemRequest: ItemRequest, itemList: List<Item>): ItemRequestDto {
        val itemRequestDto = delegate.toDto(itemRequest, itemList)

        return itemRequestDto.copy(items = itemMapper.toDto(itemList))
    }

    override fun toDto(itemRequestList: List<ItemRequest>, itemList: List<Item>): List<ItemRequestDto> {
        val requestIdToItemList = itemList.stream()
            .collect(Collectors.groupingBy { item -> item.request!!.id })

        return itemRequestList.stream()
            .map { itemRequest -> toDto(itemRequest, requestIdToItemList.getOrDefault(itemRequest.id, emptyList())) }
            .toList()
    }
}
