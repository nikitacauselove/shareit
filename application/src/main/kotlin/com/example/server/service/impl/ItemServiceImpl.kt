package com.example.server.service.impl

import com.example.api.dto.ItemDto
import com.example.api.dto.ItemDtoWithBookings
import com.example.server.exception.NotFoundException
import com.example.server.mapper.ItemMapper
import com.example.server.repository.BookingRepository
import com.example.server.repository.CommentRepository
import com.example.server.repository.FromSizePageRequest.Companion.of
import com.example.server.repository.ItemRepository
import com.example.server.repository.ItemRequestRepository
import com.example.server.repository.UserRepository
import com.example.server.repository.entity.Item
import com.example.server.service.ItemService
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ItemServiceImpl(
    private val bookingRepository: BookingRepository,
    private val commentRepository: CommentRepository,
    private val itemMapper: ItemMapper,
    private val itemRepository: ItemRepository,
    private val itemRequestRepository: ItemRequestRepository,
    private val userRepository: UserRepository
) : ItemService {

    @Transactional
    override fun create(itemDto: ItemDto, userId: Long): Item {
        val owner = userRepository.findById(userId)
            .orElseThrow { NotFoundException("Пользователь с указанным идентификатором не найден") }
        val itemRequest = if (itemDto.requestId == null) null else itemRequestRepository.findById(itemDto.requestId!!)
            .orElseThrow { NotFoundException("Запрос на добавление предмета с указанным идентификатором не найден") }

        return itemRepository.save(itemMapper.toItem(itemDto, owner, itemRequest))
    }

    @Transactional
    override fun update(id: Long, itemDto: ItemDto, userId: Long): Item {
        val item = findById(id)
        val ownerId = item.owner!!.id

        if (ownerId != userId) {
            throw NotFoundException("Обновлять информацию о предмете может только его владелец")
        }
        return itemRepository.save(itemMapper.updateItem(itemDto, item))
    }

    override fun findById(id: Long): Item {
        return itemRepository.findById(id)
            .orElseThrow { NotFoundException("Предмет с указанным идентификатором не найден") }
    }

    override fun findByIdWithBooking(id: Long, userId: Long): ItemDtoWithBookings {
        val item = findById(id)
        val ownerId = item.owner!!.id

        if (ownerId == userId) {
            return itemMapper.toItemDtoWithBookings(
                item,
                bookingRepository.findAllByItemId(id),
                commentRepository.findAllByItemId(id)
            )
        }
        return itemMapper.toItemDtoWithBookings(item, commentRepository.findAllByItemId(id))
    }

    override fun findAllByOwnerId(userId: Long, from: Int, size: Int): List<ItemDtoWithBookings> {
        val items = itemRepository.findAllByOwnerId(userId, of(from, size, Sort.by(Sort.Direction.ASC, "id")))
        val bookings = bookingRepository.findAllByItem_Owner_Id(userId, Pageable.unpaged())
        val comments = commentRepository.findAllByOwnerId(userId)

        return itemMapper.toItemDtoWithBookings(items, bookings, comments)
    }

    override fun search(text: String, from: Int, size: Int): List<Item> {
        if (text.isBlank()) {
            return emptyList()
        }
        return itemRepository.search(text.lowercase(Locale.getDefault()), of(from, size))
    }
}
