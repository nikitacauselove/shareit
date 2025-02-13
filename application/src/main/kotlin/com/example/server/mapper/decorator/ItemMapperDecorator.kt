package com.example.server.mapper.decorator

import com.example.api.model.BookingShortDto
import com.example.api.model.ItemDtoWithBooking
import com.example.api.model.BookingStatus
import com.example.server.mapper.CommentMapper
import com.example.server.mapper.ItemMapper
import com.example.server.repository.entity.Booking
import com.example.server.repository.entity.Comment
import com.example.server.repository.entity.Item
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import java.util.stream.Collectors

abstract class ItemMapperDecorator : ItemMapper {

    private var commentMapper: CommentMapper? = null
    private var delegate: ItemMapper? = null

    override fun toItemDtoWithBooking(item: Item, bookingList: List<Booking>, commentList: List<Comment>): ItemDtoWithBooking {
        return ItemDtoWithBooking(
            id = item.id!!,
            name = item.name,
            description = item.description,
            available = item.available,
            lastBooking = findLastBooking(bookingList),
            nextBooking = findNextBooking(bookingList),
            comments = commentMapper!!.toCommentDto(commentList)
        )
    }

    override fun toItemDtoWithBooking(items: List<Item>, bookingList: List<Booking>, commentList: List<Comment>): List<ItemDtoWithBooking> {
        return items.stream()
            .map { item: Item ->
                val bookings = bookingList.stream()
                    .filter { booking: Booking -> booking.item.id == item.id }
                    .collect(Collectors.toList())
                val comments = commentList.stream()
                    .filter { comment: Comment -> comment.item.id == item.id }
                    .collect(Collectors.toList())

                this.toItemDtoWithBooking(item, bookings, comments)
            }
            .collect(Collectors.toList())
    }

    private fun findLastBooking(bookingList: List<Booking>): BookingShortDto? {
        val now = LocalDateTime.now()

        return bookingList.stream()
            .sorted(BY_START_DESCENDING)
            .filter { booking: Booking -> booking.status == BookingStatus.APPROVED && booking.start.isBefore(now) }
            .findFirst()
            .map { booking: Booking -> BookingShortDto(booking.id!!, booking.start, booking.end, booking.booker.id!!) }
            .orElse(null)
    }

    private fun findNextBooking(bookingList: List<Booking>): BookingShortDto? {
        val now = LocalDateTime.now()

        return bookingList.stream()
            .sorted(BY_START_ASCENDING)
            .filter { booking: Booking -> booking.status == BookingStatus.APPROVED && booking.start.isAfter(now) }
            .findFirst()
            .map { booking: Booking -> BookingShortDto(booking.id!!, booking.start, booking.end, booking.booker.id!!) }
            .orElse(null)
    }

    @Autowired
    fun setCommentMapper(commentMapper: CommentMapper) {
        this.commentMapper = commentMapper
    }

    @Autowired
    fun setDelegate(itemMapper: ItemMapper) {
        this.delegate = itemMapper
    }

    companion object {
        private val BY_START_ASCENDING = Comparator.comparing(Booking::start)
        private val BY_START_DESCENDING = BY_START_ASCENDING.reversed()
    }
}