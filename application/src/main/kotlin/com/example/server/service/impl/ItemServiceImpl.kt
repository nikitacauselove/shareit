package com.example.server.service.impl

import com.example.api.dto.ItemDto
import com.example.api.dto.ItemDtoWithBooking
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
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException

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
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь с указанным идентификатором не найден") }
        val itemRequest = if (itemDto.requestId == null) null else itemRequestRepository.findById(itemDto.requestId!!)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Запрос на добавление предмета с указанным идентификатором не найден") }

        return itemRepository.save(itemMapper.toItem(itemDto, owner, itemRequest))
    }

    @Transactional
    override fun update(id: Long, itemDto: ItemDto, userId: Long): Item {
        val item = findById(id)
        val ownerId = item.owner.id

        if (userId != ownerId) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Обновлять информацию о предмете может только его владелец")
        }
        return itemMapper.updateItem(itemDto, item)
    }

    override fun findById(id: Long): Item {
        return itemRepository.findById(id)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Предмет с указанным идентификатором не найден") }
    }

    @Transactional(readOnly = true)
    override fun findByIdWithBooking(id: Long, userId: Long): ItemDtoWithBooking {
        val item = findById(id)
        val ownerId = item.owner.id

        if (userId != ownerId) {
            return itemMapper.toItemDtoWithBooking(item, emptyList(), commentRepository.findAllByItemId(id))
        }
        return itemMapper.toItemDtoWithBooking(item, bookingRepository.findAllByItemId(id), commentRepository.findAllByItemId(id))
    }

    @Transactional(readOnly = true)
    override fun findAllByOwnerId(userId: Long, from: Int, size: Int): List<ItemDtoWithBooking> {
        val pageable = of(from, size, SORT_BY_DESCENDING_ID)
        val items = itemRepository.findAllByOwnerId(userId, pageable)
        val bookings = bookingRepository.findAllByItem_Owner_Id(userId, Pageable.unpaged())
        val comments = commentRepository.findAllByOwnerId(userId)

        return itemMapper.toItemDtoWithBooking(items, bookings, comments)
    }

    override fun search(text: String, from: Int, size: Int): List<Item> {
        return if (text.isBlank()) emptyList() else itemRepository.search(text.lowercase(), of(from, size))
    }

    companion object {
        private val SORT_BY_DESCENDING_ID = Sort.by(Sort.Direction.ASC, "id")
    }
}
