package com.example.server.mapper.decorator

import com.example.api.model.ItemDtoWithBooking
import com.example.server.mapper.ItemMapper
import com.example.server.entity.Booking
import com.example.server.entity.Item
import com.example.server.mapper.findLast
import com.example.server.mapper.findNext
import com.example.server.mapper.toDto
import org.springframework.beans.factory.annotation.Autowired
import java.util.stream.Collectors

abstract class ItemMapperDecorator : ItemMapper {

    @Autowired
    private lateinit var delegate: ItemMapper

    override fun toDtoWithBooking(item: Item, bookingList: List<Booking>): ItemDtoWithBooking {
        val itemDtoWithBooking = delegate.toDtoWithBooking(item, bookingList)

        return itemDtoWithBooking.copy(lastBooking = bookingList.findLast(), nextBooking = bookingList.findNext(), comments = item.comments.toDto())
    }

    override fun toDtoWithBooking(items: List<Item>, bookingList: List<Booking>): List<ItemDtoWithBooking> {
        return items.stream()
            .map { item: Item ->
                val bookings = bookingList.stream()
                    .filter { booking: Booking -> booking.item.id == item.id }
                    .collect(Collectors.toList())

                this.toDtoWithBooking(item, bookings)
            }
            .collect(Collectors.toList())
    }
}