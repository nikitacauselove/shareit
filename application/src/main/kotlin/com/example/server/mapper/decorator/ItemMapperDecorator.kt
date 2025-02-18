package com.example.server.mapper.decorator

import com.example.api.model.BookingShortDto
import com.example.api.model.ItemDtoWithBooking
import com.example.server.mapper.CommentMapper
import com.example.server.mapper.ItemMapper
import com.example.server.entity.Booking
import com.example.server.entity.BookingStatus
import com.example.server.entity.Comment
import com.example.server.entity.Item
import org.springframework.beans.factory.annotation.Autowired
import java.time.LocalDateTime
import java.util.stream.Collectors

abstract class ItemMapperDecorator : ItemMapper {

    @Autowired
    private lateinit var commentMapper: CommentMapper

    @Autowired
    private lateinit var delegate: ItemMapper

    override fun toDtoWithBooking(item: Item, bookingList: List<Booking>, commentList: List<Comment>): ItemDtoWithBooking {
        val itemDtoWithBooking = delegate.toDtoWithBooking(item, bookingList, commentList)

        return itemDtoWithBooking.copy(lastBooking = findLastBooking(bookingList), nextBooking = findNextBooking(bookingList), comments = commentMapper.toDto(commentList))
    }

    override fun toDtoWithBooking(items: List<Item>, bookingList: List<Booking>, commentList: List<Comment>): List<ItemDtoWithBooking> {
        return items.stream()
            .map { item: Item ->
                val bookings = bookingList.stream()
                    .filter { booking: Booking -> booking.item.id == item.id }
                    .collect(Collectors.toList())
                val comments = commentList.stream()
                    .filter { comment: Comment -> comment.item.id == item.id }
                    .collect(Collectors.toList())

                this.toDtoWithBooking(item, bookings, comments)
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

    companion object {
        private val BY_START_ASCENDING = Comparator.comparing(Booking::start)
        private val BY_START_DESCENDING = BY_START_ASCENDING.reversed()
    }
}