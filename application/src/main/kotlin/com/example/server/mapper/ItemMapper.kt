package com.example.server.mapper

import com.example.api.dto.BookingShortDto
import com.example.api.dto.CommentDto
import com.example.api.dto.ItemDto
import com.example.api.dto.ItemDtoWithBookings
import com.example.api.dto.enums.BookingStatus
import com.example.server.repository.entity.Booking
import com.example.server.repository.entity.Comment
import com.example.server.repository.entity.Item
import com.example.server.repository.entity.ItemRequest
import com.example.server.repository.entity.User
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.stream.Collectors

@Component
class ItemMapper(
    private val commentMapper: CommentMapper
) {

    fun toItem(itemDto: ItemDto, owner: User, itemRequest: ItemRequest?): Item {
        val item = Item()

        item.name = itemDto.name
        item.description = itemDto.description
        item.available = itemDto.available
        item.owner = owner
        item.request = itemRequest

        return item
    }

    fun updateItem(itemDto: ItemDto, item: Item): Item {
        if (itemDto.name != null) {
            item.name = itemDto.name
        }
        if (itemDto.description != null) {
            item.description = itemDto.description
        }
        if (itemDto.available != null) {
            item.available = itemDto.available
        }

        return item
    }

    fun toItemDto(item: Item): ItemDto {
        var requestId: Long? = null
        var id: Long? = null
        var name: String? = null
        var description: String? = null
        var available: Boolean? = null

        requestId = itemRequestId(item)
        id = item.id
        name = item.name
        description = item.description
        available = item.available

        val itemDto = ItemDto(id, name, description, available, requestId)

        return itemDto
    }

    fun toItemDto(items: List<Item>): List<ItemDto> {
        val list: MutableList<ItemDto> = ArrayList(items.size)
        for (item in items) {
            list.add(toItemDto(item))
        }

        return list
    }

    fun toItemDtoWithBookings(item: Item, bookings: List<Booking>, comments: List<Comment>): ItemDtoWithBookings {
        val lastBooking = findLastBooking(bookings)
        val nextBooking = findNextBooking(bookings)

        return ItemDtoWithBookings(
            item.id!!,
            item.name!!,
            item.description!!,
            item.available!!,
            lastBooking,
            nextBooking,
            commentMapper.toCommentDto(comments)
        )
    }

    fun toItemDtoWithBookings(item: Item, comments: List<Comment>): ItemDtoWithBookings {
        var id = 0L
        var name: String? = null
        var description: String? = null
        var available = false

        if (item.id != null) {
            id = item.id!!
        }
        name = item.name
        description = item.description
        if (item.available != null) {
            available = item.available!!
        }
        var comments1: List<CommentDto?>? = null
        comments1 = commentMapper.toCommentDto(comments)

        val lastBooking: BookingShortDto? = null
        val nextBooking: BookingShortDto? = null

        val itemDtoWithBookings = ItemDtoWithBookings(
            id,
            name!!, description!!, available, lastBooking, nextBooking, comments1
        )

        return itemDtoWithBookings
    }

    fun toItemDtoWithBookings(
        items: List<Item>,
        bookings: List<Booking>,
        comments: List<Comment>
    ): List<ItemDtoWithBookings> {
        return items.stream()
            .map { item: Item ->
                val listOfBookings = bookings.stream().filter { booking: Booking -> booking.item!!.id == item.id }
                    .collect(Collectors.toList())
                val listOfComments = comments.stream().filter { comment: Comment -> comment.item!!.id == item.id }
                    .collect(Collectors.toList())
                this.toItemDtoWithBookings(item, listOfBookings, listOfComments)
            }
            .collect(Collectors.toList())
    }

    private fun itemRequestId(item: Item): Long? {
        val request = item.request ?: return null
        return request.id
    }

    protected fun itemToItemDtoWithBookings(
        item: Item?,
        bookings: List<Booking?>?,
        comments: List<Comment?>?
    ): ItemDtoWithBookings? {
        if (item == null) {
            return null
        }

        var id = 0L
        var name: String? = null
        var description: String? = null
        var available = false

        if (item.id != null) {
            id = item.id!!
        }
        name = item.name
        description = item.description
        if (item.available != null) {
            available = item.available!!
        }

        val lastBooking: BookingShortDto? = null
        val nextBooking: BookingShortDto? = null
        val comments1: List<CommentDto>? = null

        val itemDtoWithBookings = ItemDtoWithBookings(
            id, name!!, description!!, available, lastBooking, nextBooking,
            comments1!!
        )

        return itemDtoWithBookings
    }

    private fun findLastBooking(bookings: List<Booking>): BookingShortDto? {
        val now = LocalDateTime.now()

        return bookings.stream()
            .sorted(BY_START_DESCENDING)
            .filter { booking: Booking -> booking.status == BookingStatus.APPROVED && booking.start!!.isBefore(now) }
            .findFirst()
            .map { booking: Booking ->
                BookingShortDto(
                    booking.id!!,
                    booking.start!!,
                    booking.end!!,
                    booking.booker!!.id!!
                )
            }
            .orElse(null)
    }

    private fun findNextBooking(bookings: List<Booking>): BookingShortDto? {
        val now = LocalDateTime.now()

        return bookings.stream()
            .sorted(BY_START_ASCENDING)
            .filter { booking: Booking -> booking.status == BookingStatus.APPROVED && booking.start!!.isAfter(now) }
            .findFirst()
            .map { booking: Booking ->
                BookingShortDto(
                    booking.id!!,
                    booking.start!!,
                    booking.end!!,
                    booking.booker!!.id!!
                )
            }
            .orElse(null)
    }

    companion object {
        private val BY_START_ASCENDING: Comparator<Booking> = Comparator.comparing { obj: Booking -> obj.start }
        private val BY_START_DESCENDING: Comparator<Booking> = BY_START_ASCENDING.reversed()
    }
}
