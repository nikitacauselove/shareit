package com.example.server.mapper

import com.example.api.dto.ItemDto
import com.example.api.dto.ItemRequestDto
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.ItemRequest
import com.example.server.repository.entity.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.function.Function
import java.util.stream.Collectors

@Component
class ItemRequestMapper(
    private val itemMapper: ItemMapper
) {

    fun toItemRequest(itemRequestDto: ItemRequestDto, requester: User): ItemRequest {
        return ItemRequest(
            id = null,
            description = itemRequestDto.description!!,
            requester = requester,
            created = null
        )
    }

    fun toItemRequestDto(itemRequest: ItemRequest, items: List<Item>): ItemRequestDto {

        var requesterId: Long? = null
        var id: Long? = null
        var description: String? = null
        var created: LocalDateTime? = null
        if (itemRequest != null) {
            requesterId = itemRequestRequesterId(itemRequest)
            id = itemRequest.id
            description = itemRequest.description
            created = itemRequest.created
        }
        var items1: List<ItemDto?>? = null
        items1 = itemMapper!!.toItemDto(items)

        val itemRequestDto = ItemRequestDto(id, description, requesterId, created, items1)

        return itemRequestDto
    }

    fun toItemRequestDto(itemRequests: List<ItemRequest>, items: List<Item>): MutableList<ItemRequestDto> {
        val map = items.stream()
            .collect(
                Collectors.groupingBy(
                    Function { item: Item -> item.request!!.id })
            )

        return itemRequests.stream()
            .map { itemRequest: ItemRequest ->
                toItemRequestDto(
                    itemRequest,
                    map.getOrDefault(itemRequest.id, emptyList())
                )
            }
            .collect(Collectors.toList())
    }

    private fun itemRequestRequesterId(itemRequest: ItemRequest): Long? {
        val requester = itemRequest.requester ?: return null
        return requester.id
    }

    protected fun itemRequestToItemRequestDto(itemRequest: ItemRequest?, items: List<Item?>?): ItemRequestDto? {
        if (itemRequest == null) {
            return null
        }

        var id: Long? = null
        var description: String? = null
        var created: LocalDateTime? = null

        id = itemRequest.id
        description = itemRequest.description
        created = itemRequest.created

        val requesterId: Long? = null
        val items1: List<ItemDto>? = null

        val itemRequestDto = ItemRequestDto(id, description, requesterId, created, items1)

        return itemRequestDto
    }
}
